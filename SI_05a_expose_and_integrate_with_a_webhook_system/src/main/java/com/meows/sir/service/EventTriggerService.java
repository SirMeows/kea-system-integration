package com.meows.sir.service;

import com.meows.sir.entity.EventType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EventTriggerService {
    public void triggerEvents() {

        printEventNotificationToConsole(EventType.LIST_CREATED, "exampleUrl");

        sendEventNotifications(EventType.LIST_CREATED);
        sendEventNotifications(EventType.LIST_MODIFIED);
        sendEventNotifications(EventType.LIST_DELETED);

    }

    private void sendEventNotifications(EventType eventType) {
        //determine where to send different event notifications
        //by retrieving relevant webhook URLs from WebhookService
        //Make POST requests to client webhooks
    }

    private static void printEventNotificationToConsole(EventType eventType, String webhookUrl) {
        System.out.println("Notification of event type " + eventType + " sent to " + webhookUrl);
    }
}
