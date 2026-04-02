package com.proxy.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proxy.demo.service.ProxyService;

@RestController
@RequestMapping("/auth")
public class AuthProxyController {

    private final ProxyService proxyService;

    public AuthProxyController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String body) {
        return proxyService.forward("/api/auth/login", body);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String body) {
        return proxyService.forward("/api/auth/register", body);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(@RequestHeader("Authorization") String token) {
        return proxyService.me(token);
    }

}