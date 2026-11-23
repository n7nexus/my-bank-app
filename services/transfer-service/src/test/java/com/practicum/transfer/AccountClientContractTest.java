package com.practicum.transfer;

import com.practicum.transfer.client.AccountClient;
import com.practicum.transfer.dto.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "com.practicum:account-service:+:stubs:8085",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)

@ImportAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class AccountClientContractTest {

    @Autowired
    private AccountClient accountsClient;

    @Test
    void shoudTransferSuccessfully() {
        var result = accountsClient.transfer(
                new TransferRequest("tu1", "tu2", BigDecimal.valueOf(200))
        );

        assertEquals("Денис", result.fullName());
        assertEquals(BigDecimal.valueOf(400), result.amount());
    }

    @Test
    void shoudTransferUnsuccessfullyNotEnoughMoney() {
        var exception = assertThrows(WebClientResponseException.class,
                () -> accountsClient.transfer(
                        new TransferRequest("tu1", "tu2", BigDecimal.valueOf(500))
                )
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void shoudTransferUnsuccessfullyNoSuchAccount() {
        var exception = assertThrows(WebClientResponseException.class,
                () -> accountsClient.transfer(
                        new TransferRequest("tu1", "tu3", BigDecimal.valueOf(100))
                )
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}
