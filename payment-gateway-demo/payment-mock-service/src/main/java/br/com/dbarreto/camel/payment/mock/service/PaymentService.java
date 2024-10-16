package br.com.dbarreto.camel.payment.mock.service;

import br.com.dbarreto.camel.payment.mock.model.PaymentRequest;
import br.com.dbarreto.camel.payment.mock.model.PaymentResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    public PaymentResponse processPayment(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(UUID.randomUUID().toString());
        response.setTransactionId(request.getTransactionId());
        response.setAmount(request.getAmount());
        response.setCurrency(request.getCurrency());
        response.setPaymentMethod(request.getPaymentMethod());

        // Randomly select a status
        String[] statuses = {"Completed", "Pending", "Failed"};
        int randomIndex = ThreadLocalRandom.current().nextInt(statuses.length);
        response.setStatus(statuses[randomIndex]);

        // Generate a random payment date and time (within the past year)
        LocalDateTime randomDate = LocalDateTime.now()
                .minusDays(ThreadLocalRandom.current().nextInt(365))
                .minusHours(ThreadLocalRandom.current().nextInt(24))
                .minusMinutes(ThreadLocalRandom.current().nextInt(60));
        response.setPaymentDate(randomDate);

        return response;
    }
}

