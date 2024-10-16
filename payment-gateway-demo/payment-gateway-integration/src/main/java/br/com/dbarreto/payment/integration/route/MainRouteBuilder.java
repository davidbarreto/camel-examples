package br.com.dbarreto.payment.integration.route;

import br.com.dbarreto.payment.integration.aggregate.ResponseAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Class to configure routes. We can have more than one class configuring multiple routes
 */
@Component
public class MainRouteBuilder extends RouteBuilder {

    /**
     * Configure routes in Camel Context
     */
    @Override
    public void configure() {

        // My entry point route
        // Read all files in "payments/request" folder
        from("file:{{app.payments.path}}?move=done&moveFailed=error").routeId("fileProcessingRoute")
            // CSV records to List<PaymentRequest>
            .unmarshal("requestCsvDataFormat")
            // Process each request (PaymentRequest) separately
            .split(body(), new ResponseAggregationStrategy())
                // Call utility route
                .to("direct:processRequest")
            .end()
            // List<PaymentResponse> to CSV records
            .marshal("responseCsvDataFormat")
            // Save CSV file
            .to("file:{{app.payments.path}}/response?fileName=${file:name}")
        .end();

        // Process each request object
        from("direct:processRequest").routeId("processRequestRoute")
            // Validate request using a Spring Bean
            .validate().method("requestValidationBean")
            // Convert PaymentRequest object to Json
            .marshal("requestJsonDataFormat")
            // Call HTTP service
            .to("http:{{service.url}}/payments")
            // Convert Json response to Java Object (PaymentResponse)
            .unmarshal("responseJsonDataFormat")
        .end();
    }
}
