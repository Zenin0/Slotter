package com.isanz.slotterws.modules.company.application.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CompanyRequestDTO {

    private String name;
    private String slug;
}
