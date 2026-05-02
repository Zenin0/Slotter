package com.isanz.slotterws.modules.role.application.dto;

import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleFullDTO {
    UUID id;
    String name;
    String description;
    Boolean isActive;
    List<UserResponseDTO> users;
}
