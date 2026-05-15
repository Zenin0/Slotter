package com.isanz.slotterws.modules.customer.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerRequestDTO {
    String name;
    String email;
    String phone;
    UUID company;
}
