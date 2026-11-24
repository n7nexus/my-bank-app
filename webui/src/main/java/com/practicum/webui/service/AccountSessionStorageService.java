package com.practicum.webui.service;

import com.practicum.webui.dto.account.AccountInfo;
import com.practicum.webui.dto.account.AccountShortInfo;
import com.practicum.webui.dto.cash.CashInfo;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountSessionStorageService {

    private String name;
    private LocalDate birthdate;
    private BigDecimal sum;
    private List<AccountShortInfo> accounts;

    public void storeUserData(AccountInfo accountInfo) {
        name = accountInfo.fullName();
        birthdate = accountInfo.birthdate();
        sum = accountInfo.money();
    }

    public void storeUserData(CashInfo cashInfo) {
        name = cashInfo.fullName();
        sum = cashInfo.amount();
    }

    public void resetUserData() {
        name = "";
        birthdate = null;
        sum = BigDecimal.ZERO;
    }

    public void storeAccountList(List<AccountShortInfo> accounts) {
        this.accounts = accounts;
    }

    public void fillModel(Model model, @Nullable List<String> errors, @Nullable String info) {
        model.addAttribute("name", name);
        if(birthdate != null) {
            model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ISO_DATE));
        }
        model.addAttribute("sum", sum);
        model.addAttribute("accounts", accounts);
        model.addAttribute("errors", errors);
        model.addAttribute("info", info);
    }

    public void fillModel(Model model, @Nullable List<String> errors) {
        fillModel(model, errors, (String) model.getAttribute("info"));
    }

}
