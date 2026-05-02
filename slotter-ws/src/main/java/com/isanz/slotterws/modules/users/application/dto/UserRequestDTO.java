package com.isanz.slotterws.modules.users.application.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class UserRequestDTO {
    String email;
    String username;
    String password;
    UUID companyId;
    Boolean isActive;
}
