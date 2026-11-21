package com.practicum.webui.dto.cash;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashInfo(String fullName, @NotNull BigDecimal amount) {
}
