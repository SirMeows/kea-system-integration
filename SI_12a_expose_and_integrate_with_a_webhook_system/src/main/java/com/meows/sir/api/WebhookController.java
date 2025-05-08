package com.meows.sir.api;

import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.dto.WebhookRegistrationDto;
import com.meows.sir.service.EventTriggerService;
import com.meows.sir.service.WebhookService;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Getter
@Setter
public class WebhookController {
    private EventTriggerService eventTriggerService;
    private WebhookService webhookService;
    private ModelMapper modelMapper;

    @PermitAll
    @PostMapping("/ping")
    public Mono<Void> pingServer() {
        return eventTriggerService.triggerEvents();
    }

    @PermitAll
    @PostMapping("/register")
    public Mono<WebhookRegistration> registerUrl(@RequestBody WebhookRegistrationDto body) {
        var webhookRegistration = modelMapper.map(body, WebhookRegistration.class);
        return webhookService.registerWebhook(webhookRegistration);
    }

    @DeleteMapping("/clear-webhooks")
    public Mono<Void> clearWebhooks() {
        return webhookService.clearWebhookRepository();
    }
}
