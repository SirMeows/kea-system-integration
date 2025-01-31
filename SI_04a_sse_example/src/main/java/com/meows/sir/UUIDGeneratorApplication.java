package com.meows.sir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.meows.sir")
public class UUIDGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(UUIDGeneratorApplication.class, args);
    }
}
