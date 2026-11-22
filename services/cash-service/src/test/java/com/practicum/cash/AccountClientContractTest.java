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
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldMatchContractWhenCheckingOwner() {
        var result = accountsClient.cashEdit(new CashRequest("tu1", BigDecimal.valueOf(200), CashAction.PUT));


        assertEquals("Денис", result.fullName());
        assertEquals(BigDecimal.valueOf(200), result.amount());
    }
}
