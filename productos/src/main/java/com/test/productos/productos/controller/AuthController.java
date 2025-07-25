package com.test.productos.productos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.productos.productos.security.JwtUtil;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @Data
    @Getter
    @Setter
    static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    static class TokenResponse {
        private final String token;
    }
}
