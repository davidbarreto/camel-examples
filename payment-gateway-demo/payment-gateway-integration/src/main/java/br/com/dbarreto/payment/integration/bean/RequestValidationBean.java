package br.com.dbarreto.payment.integration.bean;

import br.com.dbarreto.payment.integration.model.PaymentRequest;
import org.apache.camel.Body;
import org.springframework.stereotype.Component;

@Component
public class RequestValidationBean {
    public boolean validate(@Body PaymentRequest request) {
        return true;
    }
}
