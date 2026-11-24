package com.practicum.webui.dto.account;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountInfo(String accountId, String fullName, LocalDate birthdate, BigDecimal money) {
}
