package com.isanz.slotterws.modules.customer.application.dto;

import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CustomerFullDTO {
    String name;
    String email;
    String phone;
    List<CompanyResponseDTO> companies;
}
