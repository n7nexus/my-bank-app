package com.practicum.accounts.mapper;

import com.practicum.accounts.dto.notification.Notification;
import com.practicum.accounts.model.OutboxEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutboxMapper {
    @Mapping(source = "accountId", target = "user")
    Notification toNotification(OutboxEvent entity);
}
