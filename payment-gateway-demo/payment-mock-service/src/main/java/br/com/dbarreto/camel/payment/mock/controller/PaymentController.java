package br.com.dbarreto.camel.payment.mock.controller;

import br.com.dbarreto.camel.payment.mock.model.PaymentRequest;
import br.com.dbarreto.camel.payment.mock.model.PaymentResponse;
import br.com.dbarreto.camel.payment.mock.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }
}

