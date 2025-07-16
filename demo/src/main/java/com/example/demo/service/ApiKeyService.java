package com.example.demo.service;

import com.example.demo.model.ApiKey;
import com.example.demo.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public String generateApiKey(Long userId, String ip, String endpoint, Duration duration) {
        String rawKey = UUID.randomUUID().toString().replace("-", "");
        ApiKey apiKey = new ApiKey();
        apiKey.setKey(rawKey);
        apiKey.setUserId(userId);
        apiKey.setAllowedIp(ip);
        apiKey.setAllowedEndpoint(endpoint);
        apiKey.setExpiration(LocalDateTime.now().plus(duration));
        apiKey.setActive(true);
        apiKey.setCreatedAt(LocalDateTime.now());
        apiKeyRepository.save(apiKey);
        return rawKey;
    }

    public boolean isValid(String apiKeyValue, String clientIp, String requestPath) {
        Optional<ApiKey> apiKeyOpt = apiKeyRepository.findByKeyAndActiveTrue(apiKeyValue);
        if (apiKeyOpt.isEmpty()) return false;
        ApiKey apiKey = apiKeyOpt.get();
        if (apiKey.getExpiration().isBefore(LocalDateTime.now())) return false;

        if (apiKey.getAllowedIp() != null && !apiKey.getAllowedIp().equals(clientIp)) return false;

        if (apiKey.getAllowedEndpoint() != null && !requestPath.startsWith(apiKey.getAllowedEndpoint())) return false;

        return true;
    }

    public void revokeApiKey(String apiKeyValue) {
        apiKeyRepository.findByKeyAndActiveTrue(apiKeyValue).ifPresent(apiKey -> {
            apiKey.setActive(false);
            apiKeyRepository.save(apiKey);
        });
    }
}
