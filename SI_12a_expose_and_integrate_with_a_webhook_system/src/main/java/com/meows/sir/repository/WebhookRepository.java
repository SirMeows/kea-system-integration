package com.meows.sir.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meows.sir.entity.EventType;
import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.exception.JsonSerialisationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class WebhookRepository {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private static final String FILE_NAME = "webhook_registry.json";
    private Map<EventType, Set<WebhookRegistration>> webhooks = initializeWebhooks();

    private Map<EventType, Set<WebhookRegistration>> initializeWebhooks() {
        var webhooks = new ConcurrentHashMap<EventType, Set<WebhookRegistration>>();

        for (var eventType : EventType.values()) {
            webhooks.put(eventType, Collections.synchronizedSet(new HashSet<>()));
        }

        return webhooks;
    }

    public void registerWebhook(Mono<WebhookRegistration> registrationMono) {
            registrationMono
                    .<String>handle((registration, sink) -> {
                        try {
                            webhooks.get(registration.getEventType()).add(registration); // add registration to the set associated with the eventType
                            sink.next(objectMapper.writeValueAsString(registration));
                        } catch (JsonProcessingException e) {
                            sink.error(new JsonSerialisationException("Error serialising registration", e));
                        }
                    })
                    .onErrorResume(JsonSerialisationException.class,
                            e -> Mono.just(e.getMessage())
                    )
                    .doOnNext(this::appendToFile)
                    .subscribe();
        }

    private void appendToFile(String jsonLine) {
        try (var fileWriter = new FileWriter(resourceLoader.getResource("classpath:" + FILE_NAME).getFile(), true)){
            fileWriter.append(jsonLine).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Flux<WebhookRegistration> getWebhookRegistrations(List<EventType> eventTypes) {
        return Flux.fromIterable(eventTypes)
                .flatMapIterable(webhooks::get);
    }

}
