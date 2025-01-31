package com.meows.sir.entity;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class WebhookRegistration {
    private final UUID uuid = UUID.randomUUID();

    private final Long webhookRegisteredTime = System.currentTimeMillis();

    private String webhookUrl;

    private EventType eventType;
}
