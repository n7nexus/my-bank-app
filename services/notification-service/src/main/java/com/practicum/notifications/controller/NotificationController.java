package com.practicum.notifications.controller;

import com.practicum.notifications.dto.Notification;
import com.practicum.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/notify")
    public void postNotification(@RequestBody List<Notification> notifications) {
        if(notifications != null) {
            notificationService.send(notifications);
        }
    }

}
