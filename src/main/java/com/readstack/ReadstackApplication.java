package com.readstack;

import com.readstack.security.jwt.JWTConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {JWTConfigurationProperties.class})
public class ReadstackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadstackApplication.class, args);
    }

}
