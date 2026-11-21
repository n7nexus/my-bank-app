package com.practicum.transfer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AccountsClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );

        manager.setAuthorizedClientProvider(authorizedClientProvider);
        return manager;
    }

    @Bean
    public WebClient accountWebClient(
            OAuth2AuthorizedClientManager authorizedClientManager,
            @Value("${bank.accounts-service.base-url}") String accountsServiceBaseUrl,
            WebClient.Builder builder
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
