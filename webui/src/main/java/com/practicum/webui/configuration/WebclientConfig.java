package com.practicum.webui.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebclientConfig {

    private final OAuth2AuthorizedClientService authorizedClientService;

    private final GatewayClientProperties gatewayClientProperties;

    @Bean
    public WebClient gatewayWebClient() {
        return WebClient.builder()
                .baseUrl(gatewayClientProperties.getBaseUrl())
                .filter(addAccessTokenHeader())
                .build();
    }

    private ExchangeFilterFunction addAccessTokenHeader() {
        return (request, next) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
                OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                        oauth2Token.getAuthorizedClientRegistrationId(),
                        oauth2Token.getName()
                );

                OAuth2AccessToken accessToken = authorizedClient != null ? authorizedClient.getAccessToken() : null;

                if(accessToken != null) {
                    ClientRequest requestWithToken = ClientRequest.from(request)
                            .header("Authorization", "Bearer " + accessToken.getTokenValue())
                            .build();

                    return next.exchange(requestWithToken);
                }
            }

            return next.exchange(request);
        };
    }

}
