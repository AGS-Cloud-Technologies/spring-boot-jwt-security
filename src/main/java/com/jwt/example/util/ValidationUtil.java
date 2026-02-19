package com.jwt.example.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

    public boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 50 &&
                username.matches("^[a-zA-Z0-9._-]+$");
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 100;
    }

    public boolean isStrongPassword(String password) {
        // At least one uppercase, one lowercase, one digit, one special character
        return password != null &&
                password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}

