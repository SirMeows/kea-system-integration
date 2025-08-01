package com.meows.sir.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PaymentPayloadDto {
    private String event;
    private Instant timestamp;
    private String message;
}

