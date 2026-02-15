package com.prueba.venturessoft.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.venturessoft.security.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestParam String username) {
        
    	return jwtService.generateToken(username);
    }

}
