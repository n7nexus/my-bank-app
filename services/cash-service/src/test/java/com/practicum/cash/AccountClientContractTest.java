package com.practicum.cash;

import com.practicum.cash.client.AccountClient;
import com.practicum.cash.dto.CashRequest;
import com.practicum.cash.model.CashAction;
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
    void shoudPutCashSuccessfully() {
        var result = accountsClient.cashEdit(new CashRequest("tu1", BigDecimal.valueOf(200), CashAction.PUT));


        assertEquals("Денис", result.fullName());
        assertEquals(BigDecimal.valueOf(400), result.amount());
    }

    @Test
    void shoudPutCashUnsuccessfully() {
        var exception = assertThrows(WebClientResponseException.class, () -> accountsClient.cashEdit(new CashRequest("tu3", BigDecimal.valueOf(200), CashAction.PUT)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void shoudGetCashSuccessfully() {
        var result = accountsClient.cashEdit(new CashRequest("tu1", BigDecimal.valueOf(200), CashAction.GET));

        assertEquals("Денис", result.fullName());
        assertEquals(BigDecimal.valueOf(400), result.amount());
    }

    @Test
    void shoudGetCashUnsuccessfullyNotEnoughMoney() {
        var exception = assertThrows(WebClientResponseException.class, () -> accountsClient.cashEdit(new CashRequest("tu1", BigDecimal.valueOf(500), CashAction.GET)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void shoudGetCashUnsuccessfullyNoSuchAccount() {
        var exception = assertThrows(WebClientResponseException.class, () -> accountsClient.cashEdit(new CashRequest("tu3", BigDecimal.valueOf(200), CashAction.GET)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}
