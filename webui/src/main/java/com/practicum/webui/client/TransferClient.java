package com.practicum.webui.client;

import com.practicum.webui.dto.cash.CashInfo;
import com.practicum.webui.dto.transfer.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TransferClient {

    public static final String TRANSFER_URI = "/transfer/";
    private final WebClient client;

    public CashInfo transfer(TransferRequest transferRequest) {
        return client.post()
                .uri(TRANSFER_URI)
                .bodyValue(transferRequest)
                .retrieve()
                .bodyToMono(CashInfo.class)
                .block();
    }

    public String accountFallback() {
        return "Ошибка";
    }
}
