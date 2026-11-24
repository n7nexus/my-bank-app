package com.practicum.accounts.dto.transfer;

import java.math.BigDecimal;

public record TransferRequest(String fromAccountId, String toAccountId, BigDecimal amount) {
}
