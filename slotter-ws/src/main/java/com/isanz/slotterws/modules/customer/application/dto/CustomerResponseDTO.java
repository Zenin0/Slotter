package com.isanz.slotterws.modules.customer.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerResponseDTO {
    UUID id;
    String name;
    String email;
    String phone;
}
