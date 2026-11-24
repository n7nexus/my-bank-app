package com.practicum.webui.dto.account;

import java.time.LocalDate;

public record AccountUpdateInfo(String fullName, LocalDate birthdate) {
}
