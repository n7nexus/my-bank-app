package com.practicum.webui.dto.transfer;

import java.math.BigDecimal;

public record TransferRequest(String fromAccountId, String toAccountId, BigDecimal amount) {
}
