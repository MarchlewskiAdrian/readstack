package com.readstack.security;

import com.readstack.crud.user.UserRepository;
import com.readstack.security.jwt.JWTAuthTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.springframework.http.HttpMethod.*;

@Configuration
class SecurityConfig {
    private static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Value("${auth.jwt.public-key}")
    Resource publicKey;

    @Value("${auth.jwt.private-key}")
    Resource privateKey;

    @Bean
    KeyPair keyPair() throws GeneralSecurityException, IOException {
        RSAPublicKey rsaPublicKey = KeyReader.loadPublicKey(publicKey);
        RSAPrivateKey rsaPrivateKey = KeyReader.loadPrivateKey(privateKey);
        return new KeyPair(rsaPublicKey, rsaPrivateKey);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthTokenFilter jwtAuthTokenFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**")
                        .permitAll()

                        .requestMatchers(POST, "/token/**").permitAll()

                        .requestMatchers(GET, "/discoveries/**").permitAll()
                        .requestMatchers(POST, "/discoveries/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(PUT, "/discoveries/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(DELETE, "/discoveries/**").hasAnyRole(ADMIN, USER)

                        .requestMatchers(GET, "/categories/**").permitAll()
                        .requestMatchers(POST, "/categories/**").hasRole(ADMIN)
                        .requestMatchers(PUT, "/categories/**").hasRole(ADMIN)
                        .requestMatchers(DELETE, "/categories/**").hasRole(ADMIN)

                        .requestMatchers(GET, "/users/**").hasRole(ADMIN)
                        .requestMatchers(POST, "/users/**").permitAll()
                        .requestMatchers(PUT, "/users/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(DELETE, "/users/**").hasRole(ADMIN)

                        .requestMatchers(GET, "/votes/**").permitAll()
                        .requestMatchers(POST, "/votes/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(PUT, "/votes/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(DELETE, "/votes/**").hasAnyRole(ADMIN, USER)
                        .anyRequest().authenticated()
        );
        return http.build();
    }

}
