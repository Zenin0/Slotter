package com.isanz.slotterws.modules.session.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionResponseDTO {
    String token;
    UUID userid;
}
