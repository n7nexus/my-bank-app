package com.practicum.transfer.client;

import com.practicum.transfer.dto.notification.Notification;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NotificationClient {

    @Qualifier("notificationWebClient")
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
