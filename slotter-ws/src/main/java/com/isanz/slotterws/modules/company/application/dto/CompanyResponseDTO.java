package com.isanz.slotterws.modules.company.application.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class CompanyResponseDTO {
    UUID id;
    String name;
    String slug;
}
