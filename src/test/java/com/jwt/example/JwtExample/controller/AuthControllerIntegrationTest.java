package com.jwt.example.JwtExample.controller;

import com.jwt.example.JwtExample.dto.LoginRequest;
import com.jwt.example.JwtExample.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();

        signUpRequest = SignUpRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .fullName("Test User")
                .password("TestPassword123")
                .build();

        loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("TestPassword123")
                .build();
    }

    @Test
    public void testSignUp() throws Exception {
        var result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));

        if (result.andReturn().getResponse().getStatus() != 201) {
            System.out.println("SignUp Response: " + result.andReturn().getResponse().getContentAsString());
        }

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andDo(resultAction -> {
                    if (resultAction.getResponse().getStatus() != 201) {
                        System.out.println("Final Response Status: " + resultAction.getResponse().getStatus());
                        System.out.println("Final Response Body: " + resultAction.getResponse().getContentAsString());
                    }
                });
    }

    @Test
    public void testLogin() throws Exception {
        // First register the user
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));

        // Then login
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        LoginRequest invalidRequest = LoginRequest.builder()
                .username("nonexistentuser")
                .password("wrongpassword")
                .build();

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/public/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}

