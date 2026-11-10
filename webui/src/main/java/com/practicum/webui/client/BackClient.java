package com.practicum.webui.client;

import com.practicum.webui.configuration.BackClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class BackClient {

    private final WebClient webClient;

    public BackClient(WebClient.Builder builder, BackClientProperties properties) {
        this.webClient = builder
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
