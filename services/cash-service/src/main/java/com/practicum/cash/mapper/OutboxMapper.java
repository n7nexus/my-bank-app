package com.practicum.cash.mapper;

import com.practicum.cash.dto.notification.Notification;
import com.practicum.cash.model.OutboxEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutboxMapper {
    @Mapping(source = "accountId", target = "user")
    Notification toNotification(OutboxEvent entity);
}
