package com.meows.sir.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ShoppingListEvent {

    private UUID uuid;

    private final Long eventCreatedTime = System.currentTimeMillis();

    private EventType eventType;
}
