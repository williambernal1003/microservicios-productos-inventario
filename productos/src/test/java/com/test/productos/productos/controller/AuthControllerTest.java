package com.test.productos.productos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.test.productos.productos.security.JwtUtil;

class AuthControllerTest {

	private JwtUtil jwtUtil;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        authController = new AuthController(jwtUtil);
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
    	
        AuthController.LoginRequest request = new AuthController.LoginRequest();
        request.setUsername("admin");
        request.setPassword("1234");

        String expectedToken = "mocked-jwt-token";
        when(jwtUtil.generateToken("admin")).thenReturn(expectedToken);
        
        ResponseEntity<?> response = authController.login(request);
        
        assertTrue(response.getBody() instanceof AuthController.TokenResponse);

        AuthController.TokenResponse tokenResponse = (AuthController.TokenResponse) response.getBody();
        assertEquals(expectedToken, tokenResponse.getToken());

        verify(jwtUtil, times(1)).generateToken("admin");
    }

    @Test
    void login_ShouldReturn401_WhenCredentialsAreInvalid() {
    	
        AuthController.LoginRequest request = new AuthController.LoginRequest();
        request.setUsername("wrong");
        request.setPassword("wrong");
        
        ResponseEntity<?> response = authController.login(request);
        
        assertEquals("Credenciales inválidas", response.getBody());
    }
}
