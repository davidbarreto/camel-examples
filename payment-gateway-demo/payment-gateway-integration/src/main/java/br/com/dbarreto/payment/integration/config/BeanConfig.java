package br.com.dbarreto.payment.integration.config;

import br.com.dbarreto.payment.integration.model.PaymentRequest;
import br.com.dbarreto.payment.integration.model.PaymentResponse;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BindyCsvDataFormat requestCsvDataFormat() {
        return new BindyCsvDataFormat(PaymentRequest.class);
    }

    @Bean
    public BindyCsvDataFormat responseCsvDataFormat() {
        return new BindyCsvDataFormat(PaymentResponse   .class);
    }

    @Bean
    public JacksonDataFormat requestJsonDataFormat() {
        return new JacksonDataFormat(PaymentRequest.class);
    }

    @Bean
    public JacksonDataFormat responseJsonDataFormat() {
        return new JacksonDataFormat(PaymentResponse.class);
    }
}
