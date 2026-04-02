package com.proxy.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.demo.service.ProxyService;

@RestController
@RequestMapping("/ai")
public class AiProxyController {

    private final ProxyService proxyService;

    public AiProxyController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String body) {
        return proxyService.forward("/api/ai/chat", body);
    }

}
