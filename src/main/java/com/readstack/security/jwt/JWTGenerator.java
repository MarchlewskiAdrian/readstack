package com.readstack.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.readstack.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;


@AllArgsConstructor
@Component
class JWTGenerator {
    public static final String ROLES_CLAIM_NAME = "roles";
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JWTConfigurationProperties properties;
    private final KeyPair keyPair;

    public String authenticateAndGenerate(TokenGenerateDto dto){
        SecurityUser securityUser = authenticate(dto);

        Instant issuedAt = Instant.now(clock);
        Instant expiresAt = issuedAt.plus(Duration.ofMinutes(properties.expirationMinutes()));
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Algorithm algorithm = Algorithm.RSA256(null, privateKey);

        return JWT
                .create()
                .withSubject(securityUser.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withIssuer(properties.issuer())
                .withClaim(ROLES_CLAIM_NAME, securityUser.getRoles())
                .sign(algorithm);

    }

    private SecurityUser authenticate(TokenGenerateDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return (SecurityUser) authentication.getPrincipal();
    }
}


