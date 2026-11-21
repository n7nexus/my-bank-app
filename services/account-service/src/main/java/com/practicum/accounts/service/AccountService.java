package com.practicum.accounts.service;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.dto.account.AccountShortInfo;
import com.practicum.accounts.dto.account.AccountUpdateInfo;
import com.practicum.accounts.mapper.AccountMapper;
import com.practicum.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AccountInfo createOrUpdateAccount(String accountId, AccountUpdateInfo accountInfo) {
        var account = repository.getAccountByAccountId(accountId);
        if(account == null) {
            account = mapper.toEntity(accountId, accountInfo);
        } else {
            mapper.updateEntity(accountInfo, account);
        }

        repository.save(account);

        return mapper.toDto(account);
    }

    public AccountInfo getAccountInfo(String accountId) {
        var account = repository.getAccountByAccountId(accountId);
        return mapper.toDto(account);
    }

    public List<AccountShortInfo> getAccountList() {
        return repository.findAll().stream()
                .map(mapper::toShortDto)
                .toList();
    }


}
