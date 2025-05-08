package com.meows.sir.service;

import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.repository.WebhookRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Getter
@Setter
@Service
public class WebhookService {
    private WebhookRepository webhookRepository;

    public Mono<WebhookRegistration> registerWebhook(WebhookRegistration registration) {
        return webhookRepository.save(registration);
    }

    public Mono<Void> unregisterWebhook(WebhookRegistration webhookRegistration) {
        return webhookRepository.deleteAllByWebhookUrl(webhookRegistration.getWebhookUrl());
    }
}
