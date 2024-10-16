package br.com.dbarreto.payment.integration.aggregate;

import br.com.dbarreto.payment.integration.model.PaymentResponse;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;

public class ResponseAggregationStrategy extends AbstractListAggregationStrategy<PaymentResponse> {

    @Override
    public PaymentResponse getValue(Exchange exchange) {
        return exchange.getIn().getBody(PaymentResponse.class);
    }
}
