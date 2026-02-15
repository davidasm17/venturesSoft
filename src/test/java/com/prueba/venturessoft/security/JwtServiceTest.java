package com.prueba.venturessoft.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;
    
    private String secretKey;

    @BeforeEach
    public void setUp() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L); 
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken("user");
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken("user");
        String username = jwtService.extractUsername(token);
        assertEquals("user", username);
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken("user");
        assertTrue(jwtService.isTokenValid(token, "user"));
    }

    @Test
    public void testIsTokenInvalid() {
        String token = jwtService.generateToken("user");
        assertFalse(jwtService.isTokenValid(token, "otherUser"));
    }
}
