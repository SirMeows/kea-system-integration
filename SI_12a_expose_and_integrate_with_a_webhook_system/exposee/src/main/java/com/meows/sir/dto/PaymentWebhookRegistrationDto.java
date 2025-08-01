package com.meows.sir.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PaymentWebhookRegistrationDto {
    private String url;
    private String event;
}
