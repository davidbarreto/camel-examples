package br.com.dbarreto.payment.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ",", generateHeaderColumns = true, crlf = "UNIX")
public class PaymentResponse {

    @DataField(pos = 1)
    @JsonProperty
    private String paymentId;

    @DataField(pos = 2)
    @JsonProperty
    private String transactionId;

    @DataField(pos = 3, precision = 2)
    @JsonProperty
    private BigDecimal amount;

    @DataField(pos = 4)
    @JsonProperty
    private String currency;

    @DataField(pos = 5)
    @JsonProperty
    private String paymentDate;

    @DataField(pos = 6)
    @JsonProperty
    private String paymentMethod;

    @DataField(pos = 7)
    @JsonProperty
    private String status;
}