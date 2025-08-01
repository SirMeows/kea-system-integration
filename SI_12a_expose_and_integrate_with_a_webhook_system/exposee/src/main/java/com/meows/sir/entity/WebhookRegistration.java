package com.meows.sir.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Document
public class WebhookRegistration {
    private UUID uuid = UUID.randomUUID();

    private Instant webhookRegisteredTime = Instant.now();

    private String webhookUrl;

    private EventType eventType;
}
