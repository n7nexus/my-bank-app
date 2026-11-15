package com.practicum.webui.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "back-client")
public class GatewayClientProperties {
    private final String baseUrl;
}
