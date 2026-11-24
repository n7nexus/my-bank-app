package com.practicum.webui.dto.cash;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashRequest(@NotNull @NotBlank String accountId, @NotNull @DecimalMin(value = "0") BigDecimal amount, CashAction action) {
}
