package com.readstack.security;

import com.readstack.security.jwt.JWTAuthTokenFilter;
import com.readstack.crud.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpMethod.*;

@Configuration
class SecurityConfig {
    public static final int KEY_SIZE = 2048;
    public static final String RSA_ALGORITHM = "RSA";

    @Bean
    KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
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
        http.csrf(c -> c.disable());
        http.formLogin(c -> c.disable());
        http.httpBasic(c -> c.disable());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**")
                        .permitAll()

                        .requestMatchers(POST, "/users/**").permitAll()
                        .requestMatchers(POST, "/token/**").permitAll()

                        .requestMatchers(GET, "/discoveries/**").permitAll()
                        .requestMatchers(POST, "/discoveries/**").hasRole("ADMIN")
                        .requestMatchers(PUT, "/discoveries/**").hasRole("ADMIN")
                        .requestMatchers(DELETE, "/discoveries/**").hasRole("ADMIN")

                        .requestMatchers(GET, "/categories/**").permitAll()
                        .requestMatchers(POST, "/categories/**").hasRole("ADMIN")
                        .requestMatchers(PUT, "/categories/**").hasRole("ADMIN")
                        .requestMatchers(DELETE, "/categories/**").hasRole("ADMIN")

                        .requestMatchers(GET, "/users/**").permitAll()
                        .requestMatchers(PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(DELETE, "/users/**").hasRole("ADMIN")

                        .requestMatchers(GET, "/votes/**").permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }

}
