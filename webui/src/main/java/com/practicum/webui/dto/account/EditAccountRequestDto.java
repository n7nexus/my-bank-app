package com.practicum.webui.dto.account;

import java.time.LocalDate;

public record EditAccountRequestDto(String fullName, LocalDate birthDate) {
}
