package com.practicum.cash.dto;

import com.practicum.cash.model.CashAction;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashRequest(@NotNull @NotBlank String accountId, @NotNull @DecimalMin(value = "0") BigDecimal amount, CashAction action) {
}
