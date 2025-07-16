package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
@Setter
@Getter
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "allowed_ip")
    private String allowedIp;

    @Column(name = "allowed_endpoint")
    private String allowedEndpoint;

    @Column(name = "expiration")
    private LocalDateTime expiration;

    private boolean active;

    private LocalDateTime createdAt;
}