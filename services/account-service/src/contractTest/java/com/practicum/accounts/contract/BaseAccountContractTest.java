package com.practicum.accounts.contract;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.model.Account;
import com.practicum.accounts.service.AccountService;
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

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        when(accountService.getAccountInfo("tu1"))
                .thenReturn(new AccountInfo("tu1", "Денис", LocalDate.of(1999, 11, 12), BigDecimal.valueOf(200)));
    }
}

