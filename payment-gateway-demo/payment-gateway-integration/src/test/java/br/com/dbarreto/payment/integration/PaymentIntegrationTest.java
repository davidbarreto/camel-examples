package br.com.dbarreto.payment.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@CamelSpringBootTest
@UseAdviceWith
public class PaymentIntegrationTest {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProducerTemplate producerTemplate;

	@BeforeEach
	public void setup() throws Exception {

		// Change my entry-point component from 'file' to 'direct', so I can easily input test file through ProducerTemplate
		AdviceWith.adviceWith(camelContext, "fileProcessingRoute", a ->
				a.replaceFromWith("direct:readFile")
		);

		// Mock the HTTP service endpoint to return a predefined JSON response
		AdviceWith.adviceWith(camelContext, "processRequestRoute", routeBuilder -> {
			routeBuilder.weaveByToUri("http:\\{\\{service.url\\}\\}/payments")
					.replace()
					.to("mock:httpService");
		});

		// Mock the file endpoint to intercept the file generation
		AdviceWith.adviceWith(camelContext, "fileProcessingRoute", routeBuilder -> {
			routeBuilder.weaveByToUri("file:\\{\\{app.payments.path\\}\\}/response\\?fileName=\\$\\{file:name\\}")
					.replace()
					.to("mock:fileOutput");
		});

		camelContext.start();
	}

	@Test
	void shouldGenerateResponseCsvWhenInputFileIsCorrect() throws Exception {

		// GIVEN
		String input = new String(Files.readAllBytes(Paths.get("src/test/resources/input.csv")));
		String mockJsonResponse1 = new String(Files.readAllBytes(Paths.get("src/test/resources/mock-response-1.json")));
		String mockJsonResponse2 = new String(Files.readAllBytes(Paths.get("src/test/resources/mock-response-2.json")));
		String expectedCsvContent = new String(Files.readAllBytes(Paths.get("src/test/resources/expected-output.csv")));

		// WHEN
		// Prepare the mock endpoints
		MockEndpoint mockHttpService = camelContext.getEndpoint("mock:httpService", MockEndpoint.class);
		mockHttpService.whenAnyExchangeReceived(exchange -> {
			String body = exchange.getIn().getBody(String.class);
			if (body.contains("TRX1000")) {
				exchange.getIn().setBody(mockJsonResponse1);
			} else {
				exchange.getIn().setBody(mockJsonResponse2);
			}
		});
		mockHttpService.expectedMessageCount(2);

		MockEndpoint mockFileOutput = camelContext.getEndpoint("mock:fileOutput", MockEndpoint.class);
		mockFileOutput.expectedMessageCount(1);
		mockFileOutput.expectedBodiesReceived(expectedCsvContent);

		// Send a test file to the starting endpoint
		camelContext.createProducerTemplate().sendBodyAndHeader("direct:readFile", input, "CamelFileName", "test.csv");

		//THEN
		// Assert that the mock endpoints received the expected messages
		mockHttpService.assertIsSatisfied();
		mockFileOutput.assertIsSatisfied();
	}
}
