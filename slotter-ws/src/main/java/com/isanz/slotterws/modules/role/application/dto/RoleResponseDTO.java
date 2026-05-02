package com.isanz.slotterws.modules.role.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleResponseDTO {
    UUID id;
    String name;
    String description;
    Boolean isActive;
}
