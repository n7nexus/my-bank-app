package com.practicum.cash.service;

import com.practicum.cash.client.AccountClient;
import com.practicum.cash.dto.CashInfo;
import com.practicum.cash.dto.CashRequest;
import com.practicum.cash.mapper.CashTransactionMapper;
import com.practicum.cash.model.OutboxEvent;
import com.practicum.cash.repository.CashTransactionRepository;
import com.practicum.cash.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CashService {
    private final CashTransactionRepository repository;
    private final CashTransactionMapper mapper;
    private final OutboxRepository outboxRepository;
    private final AccountClient accountClient;

    @Transactional
    public CashInfo cashEdit(CashRequest request) {
        repository.save(mapper.toEntity(request));

        var response = accountClient.cashEdit(request);

        outboxRepository.save(OutboxEvent.builder()
                .accountId(request.accountId())
                .action("CASH_" + request.action().name())
                .time(LocalDateTime.now())
                .details("Sum: " + request.amount()
                        + "; New balance: " + response.amount().toString())
                .result("SUCCESS")
                .build());

        return response;
    }
}
