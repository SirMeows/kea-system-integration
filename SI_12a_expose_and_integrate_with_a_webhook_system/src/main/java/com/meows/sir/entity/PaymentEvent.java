package com.meows.sir.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentEvent {
    PAYMENT_RECEIVED("payment_received"),
    PAYMENT_PROCESSED("payment_processed"),
    PAYMENT_COMPLETED("payment_completed");

    private final String key;
}
