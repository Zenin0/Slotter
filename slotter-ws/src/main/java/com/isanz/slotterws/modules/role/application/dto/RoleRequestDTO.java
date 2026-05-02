package com.isanz.slotterws.modules.role.application.dto;

import lombok.Data;

@Data
public class RoleRequestDTO {
    String name;
    String description;
    Boolean isActive;
}
