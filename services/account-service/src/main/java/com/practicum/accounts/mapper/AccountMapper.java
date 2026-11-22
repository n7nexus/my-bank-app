package com.practicum.accounts.mapper;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.dto.account.AccountShortInfo;
import com.practicum.accounts.dto.account.AccountUpdateInfo;
import com.practicum.accounts.dto.cash.CashInfo;
import com.practicum.accounts.model.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "money", ignore = true)
    Account toEntity(String accountId, AccountUpdateInfo dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "money", ignore = true)
    void updateEntity(AccountUpdateInfo dto, @MappingTarget Account account);
    AccountInfo toDto(Account entity);
    AccountShortInfo toShortDto(Account entity);

    @Mapping(source = "money", target = "amount")
    CashInfo toCashInfo(Account entity);
}