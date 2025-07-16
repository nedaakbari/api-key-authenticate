package com.example.demo.controller;

import com.example.demo.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateApiKey(
            @RequestParam Long userId,
            @RequestParam(required = false) String ip,
            @RequestParam(required = false) String endpoint) {

        String key = apiKeyService.generateApiKey(userId, ip, endpoint, Duration.ofDays(30));
        return ResponseEntity.ok(key);
    }

    @PostMapping("/revoke")
    public ResponseEntity<String> revoke(@RequestParam String key) {
        apiKeyService.revokeApiKey(key);
        return ResponseEntity.ok("API Key revoked.");
    }
}