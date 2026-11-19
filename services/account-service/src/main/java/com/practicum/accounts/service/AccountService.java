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

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createOrUpdateAccount(String login, AccountUpdateInfo accountInfo) {
        var account = repository.getAccountByLogin(login);
        if(account == null) {
            account = mapper.toEntity(login, accountInfo);
        } else {
            mapper.updateEntity(accountInfo, account);
        }

        repository.save(account);
    }

    public AccountInfo getAccountInfo(String login) {
        var account = repository.getAccountByLogin(login);
        return mapper.toDto(account);
    }

    public List<AccountShortInfo> getAccountList() {
        return repository.findAll().stream()
                .map(mapper::toShortDto)
                .toList();
    }


}
