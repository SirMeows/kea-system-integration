package com.meows.sir;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Getter
@Setter
public class UUIDGeneratorController {

    @PermitAll
    @GetMapping( "/uuid-stream")
    public Flux<ServerSentEvent<String>> generateUUIDStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("Random UUID - " + UUID.randomUUID())
                        .build()
                );
    }
}
