package com.meows.sir.api;

import com.meows.sir.dto.PaymentPayloadDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
@Getter
@Setter
public class PaymentWebhookController {

    @PostMapping("/payment")
    public Mono<Void> logPayments(@RequestBody PaymentPayloadDto payload) {
        return Mono.fromRunnable(() ->
                log.info("Received webhook event: {}, message: {}, timestamp: {}",
                        payload.getEvent(),
                        payload.getMessage(),
                        payload.getTimestamp()
                )
        );
    }
}
