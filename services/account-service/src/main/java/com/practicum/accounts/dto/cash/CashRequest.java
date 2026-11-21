package com.practicum.accounts.dto.cash;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashRequest(@NotNull @DecimalMin(value = "0") BigDecimal amount, CashAction action) {
}
