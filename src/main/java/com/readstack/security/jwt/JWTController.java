package com.readstack.security.jwt;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/token")
class JWTController {
    private final JWTGenerator jwtGenerator;

    @PostMapping
    public ResponseEntity<JWTValue> getToken(@Valid @RequestBody TokenGenerateDto dto){
        String token = jwtGenerator.authenticateAndGenerate(dto);
        return ResponseEntity.ok(new JWTValue(token));

    }

}
