package com.proxy.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.demo.service.ProxyService;

@RestController
@RequestMapping("/calculator")
public class CalculatorProxyController {

    private final ProxyService proxyService;

    public CalculatorProxyController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody String body) {
        return proxyService.forward("/api/calculator/test", body);
    }

}