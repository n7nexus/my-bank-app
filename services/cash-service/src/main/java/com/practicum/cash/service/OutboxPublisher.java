package com.practicum.cash.service;

import com.practicum.cash.client.NotificationClient;
import com.practicum.cash.mapper.OutboxMapper;
import com.practicum.cash.model.OutboxEvent;
import com.practicum.cash.model.OutboxStatus;
import com.practicum.cash.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxRepository outboxRepo;
    private final NotificationClient notificationClient;
    private final OutboxMapper outboxMapper;

    @Scheduled(fixedDelay = 2000) // Запуск каждые 2 секунды
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> pending = outboxRepo.findPendingEvents();

        for (OutboxEvent event : pending) {
            notificationClient.send(outboxMapper.toNotification(event));
            event.setStatus(OutboxStatus.PUBLISHED);
            event.setProcessedAt(LocalDateTime.now());
            outboxRepo.save(event);
        }
    }
}
