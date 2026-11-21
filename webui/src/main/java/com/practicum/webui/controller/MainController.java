package com.practicum.webui.controller;

import com.practicum.webui.client.AccountClient;
import com.practicum.webui.client.CashClient;
import com.practicum.webui.client.TransferClient;
import com.practicum.webui.dto.cash.CashAction;
import com.practicum.webui.dto.account.AccountUpdateInfo;
import com.practicum.webui.dto.cash.CashRequest;
import com.practicum.webui.dto.transfer.TransferRequest;
import com.practicum.webui.service.AccountSessionStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Контроллер main.html.
 *
 * Используемая модель для main.html:
 *      model.addAttribute("name", name);
 *      model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ISO_DATE));
 *      model.addAttribute("sum", sum);
 *      model.addAttribute("accounts", accounts);
 *      model.addAttribute("errors", errors);
 *      model.addAttribute("info", info);
 *
 * Поля модели:
 *      name - Фамилия Имя текущего пользователя, String (обязательное)
 *      birthdate - дата рождения текущего пользователя, String в формате 'YYYY-MM-DD' (обязательное)
 *      sum - сумма на счету текущего пользователя, Integer (обязательное)
 *      accounts - список аккаунтов, которым можно перевести деньги, List<AccountDto> (обязательное)
 *      errors - список ошибок после выполнения действий, List<String> (не обязательное)
 *      info - строка успешности после выполнения действия, String (не обязательное)
 *
 * С примерами использования можно ознакомиться в тестовом классе заглушке AccountStub
 */

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountClient accountClient;
    private final CashClient cashClient;
    private final TransferClient transferClient;

    private final AccountSessionStorageService accountSessionStorageService;

    /**
     * GET /.
     * Редирект на GET /account
     */
    @GetMapping
    public String index() {
        return "redirect:/account";
    }

    /**
     * GET /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для получения данных аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     */
    @GetMapping("/account")
    public String getAccount(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        List<String> errors = new ArrayList<>();
        var accountInfo = accountClient.getAccountInfo(oidcUser.getPreferredUsername());
        if(accountInfo != null) {
            accountSessionStorageService.storeUserData(accountInfo);
        } else {
            accountSessionStorageService.resetUserData();
            errors.add("Данные пользователя не заданы");
        }

        var accountsList = accountClient.getAccountList();
        accountSessionStorageService.storeAccountList(accountsList);

        accountSessionStorageService.fillModel(model, errors, null);
        return "main";
    }

    /**
     * POST /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для изменения данных текущего пользователя по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Изменяемые данные:
     * 1. name - Фамилия Имя
     * 2. birthdate - дата рождения в формате YYYY-DD-MM
     */
    @PostMapping("/account")
    public String editAccount(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("birthdate") LocalDate birthdate
    ) {
        List<String> errors = new ArrayList<>();
        if(birthdate.plusYears(18).isAfter(LocalDate.now())) {
            errors.add("Возраст меньше 18 лет!!!");
        }

        if(name.isBlank()) {
            errors.add("Имя не заполнено!");
        }

        if(errors.isEmpty()) {
            var accountInfo = accountClient.createOrUpdateAccount(new AccountUpdateInfo(name, birthdate));
            accountSessionStorageService.storeUserData(accountInfo);
        }
        accountSessionStorageService.fillModel(model, errors, null);

        return "main";
    }

    /**
     * POST /cash.
     * Что нужно сделать:
     * 1. Сходить в сервис cash через Gateway API для снятия/пополнения счета текущего аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. action - GET (снять), PUT (пополнить)
     */
    @PostMapping("/cash")
    public String editCash(
            Model model,
            @RequestParam("value") BigDecimal value,
            @RequestParam("action") CashAction action,
            @AuthenticationPrincipal OidcUser oidcUser
    ) {

        List<String> errors = new ArrayList<>();

        var cashInfo = cashClient.editCash(new CashRequest(oidcUser.getPreferredUsername(), value, action));

        accountSessionStorageService.storeUserData(cashInfo);
        accountSessionStorageService.fillModel(model, errors, null);

        return "main";
    }

    /**
     * POST /transfer.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для перевода со счета текущего аккаунта на счет другого аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. login - логин пользователя получателя
     */
    @PostMapping("/transfer")
    public String transfer(
            Model model,
            @RequestParam("value") BigDecimal value,
            @RequestParam("login") String login,
            @AuthenticationPrincipal OidcUser oidcUser
    ) {

        List<String> errors = new ArrayList<>();

        var cashInfo = transferClient.transfer(new TransferRequest(oidcUser.getPreferredUsername(), login, value));

        accountSessionStorageService.storeUserData(cashInfo);
        accountSessionStorageService.fillModel(model, errors, null);

        return "main";
    }
}
