package com.practicum.notifications.service;

import com.practicum.notifications.dto.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {
    public void send(List<Notification> notifications) {
        notifications.forEach(
                n -> log.info(
                        formatAction(n.time(), n.user(), n.action(), n.details(), n.result())
                )
        );
    }

    private static String formatAction (LocalDateTime time, String user, String action, String details, String result) {
        return String.format(
                "[%s] User: %s, Action: %s, Details: %s, Result: %s",
                time, user, action, details, result
        );
    }
}
