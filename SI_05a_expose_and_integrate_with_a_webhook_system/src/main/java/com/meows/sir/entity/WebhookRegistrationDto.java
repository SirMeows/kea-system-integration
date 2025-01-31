package com.meows.sir.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class WebhookRegistrationDto {
    private String webhookUrl;

    private EventType eventType;
}
