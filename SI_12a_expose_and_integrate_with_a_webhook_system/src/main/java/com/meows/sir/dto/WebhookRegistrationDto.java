package com.meows.sir.dto;

import com.meows.sir.entity.EventType;
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
