package com.practicum.transfer.service;

import com.practicum.transfer.client.AccountClient;
import com.practicum.transfer.dto.CashInfo;
import com.practicum.transfer.dto.TransferRequest;
import com.practicum.transfer.mapper.OutboxMapper;
import com.practicum.transfer.mapper.TransferTransactionMapper;
import com.practicum.transfer.model.OutboxEvent;
import com.practicum.transfer.repository.OutboxRepository;
import com.practicum.transfer.repository.TransferTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferTransactionRepository repository;
    private final TransferTransactionMapper mapper;
    private final OutboxMapper outboxMapper;
    private final OutboxRepository outboxRepository;
    private final AccountClient accountClient;

    @Transactional
    public CashInfo transfer(TransferRequest request) {
        repository.save(mapper.toEntity(request));

        var response = accountClient.transfer(request);

        outboxRepository.save(OutboxEvent.builder()
                .accountId(request.fromAccountId())
                .action("TRANSFER")
                .time(LocalDateTime.now())
                .details("Transfer " + request.amount() 
                        + " from " + request.fromAccountId()
                        + " to " + request.toAccountId())
                .result("SUCCESS")
                .build());

        return response;
    }
}
