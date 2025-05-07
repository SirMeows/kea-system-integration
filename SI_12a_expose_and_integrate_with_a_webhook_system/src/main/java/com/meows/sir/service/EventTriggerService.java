package com.meows.sir.service;

import com.meows.sir.entity.EventType;
import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.repository.WebhookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class EventTriggerService {

    private final WebhookRepository webhookRepository;
    private final WebClient webClient = WebClient.builder().build();

    public void triggerEvents() {

        //printEventNotificationToConsole(EventType.LIST_CREATED, "exampleUrl");

        webhookRepository.getWebhookRegistrations(List.of(
                EventType.LIST_CREATED,
                EventType.LIST_DELETED,
                EventType.LIST_MODIFIED))
            .flatMap(this::sendEventNotifications)
            .subscribe();


    }

    private Mono<Void> sendEventNotifications(WebhookRegistration registration) {
        //determine where to send different event notifications
        //by retrieving relevant webhook URLs from WebhookService
        //Make POST requests to client webhooks
        var payload = Map.of(
                "event", registration.getEventType().name(),
                "data", System.currentTimeMillis()
        );

        return webClient.post()
                .uri(registration.getWebhookUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    private static void printEventNotificationToConsole(EventType eventType, String webhookUrl) {
        System.out.println("Notification of event type " + eventType + " sent to " + webhookUrl);
    }
}
