package com.meows.sir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.meows.sir")
public class ShopperBuddyNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopperBuddyNotifierApplication.class, args);
    }
}