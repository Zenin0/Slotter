package com.isanz.slotterws.modules.users.application.dto;

import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.domain.Company;
import lombok.Data;

import java.util.UUID;

@Data
public class UserFullDTO {
    private UUID id;
    private CompanyResponseDTO company;
    private String username;
    private String email;
    private String profileImage;
    private Boolean isActive;
}
