package org.example.authmodule.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwtLifecycle() {

        String email = "user@test.com";

        String token = jwtService.generateToken(email);
        String validated = jwtService.validateTokenAndGetEmail(token);

        Assertions.assertEquals(email, validated);
    }

    @Test
    void testInvalidToken() {

        String invalid = "invalid.token.value";

        Assertions.assertNull(jwtService.validateTokenAndGetEmail(invalid));
    }
}