package com.practicum.transfer.mapper;

import com.practicum.transfer.dto.TransferRequest;
import com.practicum.transfer.model.TransferTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferTransactionMapper {
    @Mapping(target = "id", ignore = true)
    TransferTransaction toEntity(TransferRequest dto);
}
