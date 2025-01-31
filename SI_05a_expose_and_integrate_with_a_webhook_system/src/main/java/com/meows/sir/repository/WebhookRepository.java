package com.meows.sir.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meows.sir.entity.WebhookRegistration;
import com.meows.sir.exception.JsonSerialisationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
@Getter
@Setter
@Service
public class WebhookRepository {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private static final String FILE_NAME = "webhook_registry.json";

        public void registerWebhook(Mono<WebhookRegistration> registration) {
            registration
                    .<String>handle((reg, sink) -> {
                        try {
                            sink.next(objectMapper.writeValueAsString(reg));
                        } catch (JsonProcessingException e) {
                            sink.error(new JsonSerialisationException("Error serialising registration", e));
                        }
                    })
                    .onErrorResume(JsonSerialisationException.class,
                            e -> Mono.just(e.getMessage())
                    )
                    .doOnNext(this::appendToFile)
                    .subscribe();
        }

    private void appendToFile(String jsonLine) {
        try (var fileWriter = new FileWriter(resourceLoader.getResource("classpath:" + FILE_NAME).getFile(), true)){
            fileWriter.append(jsonLine).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public Flux<WebhookRegistration> getWebhookRegistrations() {
        return Flux.using()
    }*/
}
