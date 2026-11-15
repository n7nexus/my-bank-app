package com.practicum.accounts.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetAccountResponseDto(String fullName, LocalDate birthDate, BigDecimal money) {
}
