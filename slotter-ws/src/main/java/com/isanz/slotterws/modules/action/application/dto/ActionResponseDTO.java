package com.isanz.slotterws.modules.action.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ActionResponseDTO {
    UUID id;
    String name;
    String description;
}
