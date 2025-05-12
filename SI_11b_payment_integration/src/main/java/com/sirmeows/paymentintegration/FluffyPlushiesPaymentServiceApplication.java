package com.sirmeows.paymentintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sirmeows.paymentintegration"})
public class FluffyPlushiesPaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FluffyPlushiesPaymentServiceApplication.class, args);
    }
}
