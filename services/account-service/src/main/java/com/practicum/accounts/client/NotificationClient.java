package com.practicum.accounts.client;

import com.practicum.accounts.dto.notification.Notification;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NotificationClient {
    private final WebClient client;

    @CircuitBreaker(name = "notificationClient", fallbackMethod = "notificationFallback")
    public void send(Notification request) {
        client.post()
            .bodyValue(request)
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    public ResponseEntity<String> notificationFallback(Throwable throwable) {
        return ResponseEntity.internalServerError().body(throwable.getMessage());
    }
}
