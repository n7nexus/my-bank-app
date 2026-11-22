package com.practicum.notifications.controller;

import com.practicum.notifications.dto.Notification;
import com.practicum.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('notification.send')")
    public void postNotification(@RequestBody Notification notification) {
        if(notification != null) {
            notificationService.send(notification);
        }
    }

}
