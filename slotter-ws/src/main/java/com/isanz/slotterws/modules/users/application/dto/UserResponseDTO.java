package com.isanz.slotterws.modules.users.application.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
public class UserResponseDTO {
    UUID id;
    String email;
    String username;
    Boolean isActive;
    UUID companyId;
}
