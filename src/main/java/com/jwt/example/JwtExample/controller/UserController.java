package com.jwt.example.JwtExample.controller;

import com.jwt.example.JwtExample.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(new ApiResponse(true, "User profile retrieved",
                authentication.getName()));
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(new ApiResponse(true, "User info retrieved",
                new UserInfo(authentication.getName(), authentication.getAuthorities().toString())));
    }

    public static class UserInfo {
        private String username;
        private String authorities;

        public UserInfo(String username, String authorities) {
            this.username = username;
            this.authorities = authorities;
        }

        public String getUsername() {
            return username;
        }

        public String getAuthorities() {
            return authorities;
        }
    }
}

