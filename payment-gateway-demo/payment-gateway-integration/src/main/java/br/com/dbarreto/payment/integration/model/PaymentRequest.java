package br.com.dbarreto.payment.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ",", skipFirstLine = true)
public class PaymentRequest {
    @DataField(pos = 1)
    @JsonProperty
    private String transactionId;
    @DataField(pos = 2, precision = 2)
    @JsonProperty
    private BigDecimal amount;

    @DataField(pos = 3)
    @JsonProperty
    private String currency;

    @DataField(pos = 4)
    @JsonProperty
    private String paymentMethod;

    @DataField(pos = 5)
    @JsonProperty
    private String cardNumber;

    @DataField(pos = 6)
    @JsonProperty
    private String cardExpiry;

    @DataField(pos = 7)
    @JsonProperty
    private Integer cvv;

    @DataField(pos = 8)
    @JsonProperty
    private String payerName;

    @DataField(pos = 9)
    @JsonProperty
    private String orderId;
}
