package com.practicum.accounts.contract;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.dto.cash.CashInfo;
import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.exception.NotEnoughMoneyException;
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
import java.util.List;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void setup() throws NoSuchAccountException, NotEnoughMoneyException {
        RestAssuredMockMvc.mockMvc(mockMvc);

        var listOfActiveAccs = List.of("tu1", "tu2");

        when(accountService.getAccountInfo("tu1"))
                .thenReturn(new AccountInfo("tu1", "Денис", LocalDate.of(1999, 11, 12), BigDecimal.valueOf(200)));

        doNothing().when(moneyService).putMoney(any(String.class), any(BigDecimal.class));
        doThrow(NoSuchAccountException.class).when(moneyService)
                .transferMoney(argThat(req ->
                            !listOfActiveAccs.contains(req.fromAccountId()) || !listOfActiveAccs.contains(req.toAccountId())
                        )
                );
        doNothing().when(moneyService).transferMoney(
                argThat(req ->
                        listOfActiveAccs.contains(req.fromAccountId())
                                && listOfActiveAccs.contains(req.toAccountId())
                                && BigDecimal.valueOf(400).compareTo(req.amount()) >= 0
                )
        );
        doThrow(NotEnoughMoneyException.class).when(moneyService).transferMoney(
                argThat(req ->
                        listOfActiveAccs.contains(req.fromAccountId())
                                && listOfActiveAccs.contains(req.toAccountId())
                                && BigDecimal.valueOf(400).compareTo(req.amount()) < 0
                )
        );

        doThrow(NoSuchAccountException.class).when(moneyService).getMoney(argThat(arg -> !listOfActiveAccs.contains(arg)), any(BigDecimal.class));
        doThrow(NoSuchAccountException.class).when(moneyService).putMoney(argThat(arg -> !listOfActiveAccs.contains(arg)), any(BigDecimal.class));

        doThrow(NotEnoughMoneyException.class).when(moneyService).getMoney(argThat(listOfActiveAccs::contains), gt(BigDecimal.valueOf(400)));

        when(moneyService.getCashInfo("tu1"))
                .thenReturn(new CashInfo("Денис", BigDecimal.valueOf(400)));

        when(moneyService.getCashInfo("tu2"))
                .thenReturn(new CashInfo("Алексей", BigDecimal.valueOf(400)));


    }
}

