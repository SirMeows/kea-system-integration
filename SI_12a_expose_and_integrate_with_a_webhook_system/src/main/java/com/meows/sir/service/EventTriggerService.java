package com.meows.sir.service;

import com.meows.sir.entity.EventType;
import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.repository.WebhookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.EnumSet;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EventTriggerService {
    private final WebhookRepository webhookRepository;
    private final WebClient webClient = WebClient.builder().build();

    public record WebhookEventPayload(UUID uuid, String eventType, Instant webhookRegisteredTime) {}

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
                Instant.now()
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
                .then();
    }

    private static void printEventNotificationToConsole(EventType eventType, String webhookUrl) {
        System.out.println("Notification of event type " + eventType + " sent to " + webhookUrl);
    }
}
