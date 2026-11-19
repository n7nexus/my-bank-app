package com.practicum.accounts.dto.account;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountInfo(String fullName, LocalDate birthDate, BigDecimal money) {
}
