package com.meows.sir.repository;

import com.meows.sir.entity.EventType;
import com.meows.sir.entity.WebhookRegistration;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface WebhookRepository extends ReactiveCrudRepository<WebhookRegistration, UUID> {
    Flux<WebhookRegistration> findAllByEventTypeIn(Collection<EventType> eventTypes);
    Mono<Void> deleteAllByWebhookUrl(String webhookUrl);
}

