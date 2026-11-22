package com.practicum.transfer.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NotificationClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedNotificationWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient notificationWebClient(
            OAuth2AuthorizedClientManager authorizedClientManager,
            @Value("${bank.notification-service.base-url}") String accountsServiceBaseUrl,
            @Qualifier("loadBalancedNotificationWebClientBuilder") WebClient.Builder builder
    ) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                authorizedClientManager
        );

        oauth2.setDefaultClientRegistrationId("transfer-service");

        return builder
                .baseUrl(accountsServiceBaseUrl)
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}
