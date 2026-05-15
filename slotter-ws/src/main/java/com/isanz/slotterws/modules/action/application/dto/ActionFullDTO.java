package com.isanz.slotterws.modules.action.application.dto;

import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ActionFullDTO {
    UUID id;
    String name;
    String description;
    Set<RoleResponseDTO> roles;
}
