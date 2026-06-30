package com.readstack.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JWTConfigurationProperties(
        long expirationMinutes,
        String issuer
) {
}
