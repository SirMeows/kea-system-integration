package com.meows.sir.service;

import com.meows.sir.entity.EventType;
import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.repository.WebhookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class EventTriggerService {
    private final WebhookRepository webhookRepository;
    private final WebClient webClient = WebClient.builder().build();

    public record WebhookEventPayload(UUID uuid, String eventType, String eventTime) {}

    public Mono<Void> triggerEvents() {
        var allEventTypes = EnumSet.allOf(EventType.class);
        return webhookRepository
                .findAllByEventTypeIn(allEventTypes)
                .flatMap(this::sendEventNotifications)
                .then();
    }

    private Mono<Void> sendEventNotifications(WebhookRegistration registration) {
        var payload = new WebhookEventPayload(
                UUID.randomUUID(),
                registration.getEventType().name(),
                DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        );

        return webClient.post()
                .uri(registration.getWebhookUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(ignored ->
                        printEventNotificationToConsole(
                                registration.getEventType(),
                                registration.getWebhookUrl()
                        )
                )
                .onErrorResume(error -> {
                    log.error("Failed to send notification to {}: {}",
                            registration.getWebhookUrl(),
                            error.getMessage());
                    return Mono.empty(); // Continue with other webhooks even if one fails
                })

                .then();
    }

    private static void printEventNotificationToConsole(EventType eventType, String webhookUrl) {
        System.out.println("Notification of event type " + eventType + " sent to " + webhookUrl);
    }
}
