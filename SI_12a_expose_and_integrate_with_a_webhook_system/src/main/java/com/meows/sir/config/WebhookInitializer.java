package com.meows.sir.config;

import com.meows.sir.dto.PaymentWebhookRegistrationDto;
import com.meows.sir.entity.PaymentEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookInitializer {

    private final WebClient.Builder webClientBuilder;

    @Value("${payment.system.url}")
    private String paymentSystemUrl;

    @Value("${application.url}")
    private String applicationUrl;

    //@PostConstruct
    public void registerWebhooks() {
        String paymentWebhookUrl = applicationUrl + "/payment";
        WebClient client = webClientBuilder.baseUrl(paymentSystemUrl).build();

        Flux.just(PaymentEvent.values())
                .map(eventType -> {
                    PaymentWebhookRegistrationDto dto = new PaymentWebhookRegistrationDto();
                    dto.setUrl(paymentWebhookUrl);
                    dto.setEvent(eventType.getKey());
                    return dto;
                })
                .flatMap(dto -> registerWebhook(client, dto))
                .subscribe(
                        null,
                        WebhookInitializer::logRegistrationFailure,
                        WebhookInitializer::logRegistrationSuccess
                );
    }

    private Mono<Void> registerWebhook(WebClient client, PaymentWebhookRegistrationDto dto) {
        return client.post()
                .uri("/register")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(v -> logEventRegistration(dto));
    }

    private static void logRegistrationSuccess() {
        log.info("Successfully registered all webhooks with partner system");
    }

    private static void logRegistrationFailure(Throwable error) {
        log.error("Failed to register webhook: {}", error.getMessage());
    }

    private static void logEventRegistration(PaymentWebhookRegistrationDto dto) {
        log.info("Registered webhook for event: {}", dto.getEvent());
    }
}
