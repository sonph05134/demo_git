package com.viettel.smsbrandname.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {

    @Value("${keycloak.credentials.secret}")
    private String secretKey;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${user-keycloak.username}")
    private String username;

    @Value("${user-keycloak.password}")
    private String password;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.PASSWORD)
            .serverUrl(authUrl)
            .realm(realm)
            .username(username)
            .password(password)
            .clientId(clientId)
            .clientSecret(secretKey)
            .build();
    }
}
