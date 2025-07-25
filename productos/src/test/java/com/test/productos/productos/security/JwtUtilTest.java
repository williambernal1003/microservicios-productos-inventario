package com.test.productos.productos.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtUtilTest {

	private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_shouldReturnValidJwt() {
        String username = "admin";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void getUsernameFromToken_shouldReturnCorrectUsername() {
        String username = "user123";
        String token = jwtUtil.generateToken(username);

        String result = jwtUtil.getUsernameFromToken(token);

        assertEquals(username, result);
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        String invalidToken = "abc.def.ghi";

        boolean isValid = jwtUtil.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForExpiredToken() throws InterruptedException {
        JwtUtil shortLivedJwt = new JwtUtil() {
            @Override
            public String generateToken(String username) {
                return Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1)) // 1 ms
                        .signWith(SignatureAlgorithm.HS512, "mi_clave_secreta_segura")
                        .compact();
            }
        };

        String token = shortLivedJwt.generateToken("testuser");
        Thread.sleep(10);
        assertFalse(shortLivedJwt.validateToken(token));
    }
}
