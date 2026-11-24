package com.practicum.transfer.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashInfo(String fullName, @NotNull BigDecimal amount) {
}
