package com.isanz.slotterws.modules.session.application.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SessionFullDTO {

    private UUID id;
    private UUID userId;
    private String token;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
