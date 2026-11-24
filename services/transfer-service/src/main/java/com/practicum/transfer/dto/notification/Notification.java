package com.practicum.transfer.dto.notification;

import java.time.LocalDateTime;

public record Notification(LocalDateTime time, String user, String action, String details, String result) {
}
