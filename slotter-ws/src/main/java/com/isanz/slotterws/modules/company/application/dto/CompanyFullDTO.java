package com.isanz.slotterws.modules.company.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyFullDTO {

    private UUID id;
    private String name;
    private String companyLogo;
    private String slug;

}
