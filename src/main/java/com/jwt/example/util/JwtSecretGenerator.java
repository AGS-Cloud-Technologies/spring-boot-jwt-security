package com.jwt.example.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtSecretGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int SECRET_KEY_LENGTH = 64; // 512 bits

    public static String generateSecretKey() {
        byte[] randomBytes = new byte[SECRET_KEY_LENGTH];
        random.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public static void main(String[] args) {
        System.out.println("Generated JWT Secret Key (use in production):");
        System.out.println(generateSecretKey());
    }
}

