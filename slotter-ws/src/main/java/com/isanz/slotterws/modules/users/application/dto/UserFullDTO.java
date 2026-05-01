package com.isanz.slotterws.modules.users.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserFullDTO {
    private UUID id;
    private UUID companyId;
    private String username;
    private String email;
    private String profileImage;
    private Boolean isActive;
}
