package com.practicum.cash.mapper;

import com.practicum.cash.dto.CashRequest;
import com.practicum.cash.model.CashTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CashTransactionMapper {
    @Mapping(target = "id", ignore = true)
    CashTransaction toEntity(CashRequest dto);
}
