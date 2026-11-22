package com.practicum.accounts.contract;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.dto.cash.CashInfo;
import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.service.AccountService;
import com.practicum.accounts.service.MoneyService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@ImportAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public abstract class BaseAccountContractTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected AccountService accountService;

    @MockitoBean
    protected MoneyService moneyService;

    @BeforeEach
    void setup() throws NoSuchAccountException {
        RestAssuredMockMvc.mockMvc(mockMvc);

        when(accountService.getAccountInfo("tu1"))
                .thenReturn(new AccountInfo("tu1", "Денис", LocalDate.of(1999, 11, 12), BigDecimal.valueOf(200)));

        doNothing().when(moneyService).putMoney("tu1", BigDecimal.valueOf(200));

        when(moneyService.getCashInfo("tu1"))
                .thenReturn(new CashInfo("Денис", BigDecimal.valueOf(200)));
    }
}

