package com.proxy.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProxyService {

    @Value("${iam.service.url}")
    private String iamUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> forward(String path, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {
            return restTemplate.exchange(iamUrl + path, HttpMethod.POST, entity, String.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // 4xx errors — pass the message through
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            // 5xx errors — return a clean 400 with a safe message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong email or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service unavailable.");
        }
    }

    public ResponseEntity<String> me(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(iamUrl + "/api/users/me", HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
