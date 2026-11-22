package com.practicum.webui.client;

import com.practicum.webui.dto.account.AccountInfo;
import com.practicum.webui.dto.account.AccountShortInfo;
import com.practicum.webui.dto.account.AccountUpdateInfo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountClient {
    public static final String ACCOUNT_URI = "/account/";
    private final WebClient client;

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public AccountInfo getAccountInfo(String accountId) {
        return client.get()
        .uri(ACCOUNT_URI + accountId)
        .retrieve()
        .bodyToMono(AccountInfo.class)
        .block();
    }

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public List<AccountShortInfo> getAccountList() {
        return client.get()
                .uri(ACCOUNT_URI)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AccountShortInfo>>() {})
                .block();
    }

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public AccountInfo createOrUpdateAccount(AccountUpdateInfo accountInfo) {
        return client.post()
                .uri(ACCOUNT_URI)
                .bodyValue(accountInfo)
                .retrieve()
                .bodyToMono(AccountInfo.class)
                .block();
    }

    public ResponseEntity<String> accountFallback(Throwable throwable) {
        return ResponseEntity.internalServerError().body(throwable.getMessage());
    }
}
