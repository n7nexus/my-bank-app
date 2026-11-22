package com.practicum.transfer.service;

import com.practicum.transfer.client.NotificationClient;
import com.practicum.transfer.mapper.OutboxMapper;
import com.practicum.transfer.model.OutboxEvent;
import com.practicum.transfer.model.OutboxStatus;
import com.practicum.transfer.repository.OutboxRepository;
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
