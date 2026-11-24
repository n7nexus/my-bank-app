package com.practicum.transfer.mapper;

import com.practicum.transfer.dto.notification.Notification;
import com.practicum.transfer.model.OutboxEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutboxMapper {
    @Mapping(source = "accountId", target = "user")
    Notification toNotification(OutboxEvent entity);
}
