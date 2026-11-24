package com.practicum.webui.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountShortInfo(
        @JsonProperty("accountId") String login,
        @JsonProperty("fullName") String name
) {}
