package com.meows.sir.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PaymentWebhookPayloadDto {
    private String event;
    private Instant timestamp;
    private String message;
}

