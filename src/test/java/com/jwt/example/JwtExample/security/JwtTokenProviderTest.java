package com.jwt.example.JwtExample.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String testUsername;
    private String testToken;

    @BeforeEach
    public void setUp() {
        testUsername = "testuser";
        testToken = jwtTokenProvider.generateToken(testUsername);
    }

    @Test
    public void testGenerateToken() {
        assertNotNull(testToken);
        assertFalse(testToken.isEmpty());
    }

    @Test
    public void testValidateToken() {
        assertTrue(jwtTokenProvider.validateToken(testToken));
    }

    @Test
    public void testGetUsernameFromToken() {
        String username = jwtTokenProvider.getUsernameFromToken(testToken);
        assertEquals(testUsername, username);
    }

    @Test
    public void testValidateInvalidToken() {
        String invalidToken = "invalid.token.string";
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
}

