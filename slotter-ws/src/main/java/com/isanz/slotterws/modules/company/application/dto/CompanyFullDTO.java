package com.isanz.slotterws.modules.company.application.dto;

import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyFullDTO {

    private UUID id;
    private String name;
    private String companyLogo;
    private String slug;
    private List<UserResponseDTO> users;

}
