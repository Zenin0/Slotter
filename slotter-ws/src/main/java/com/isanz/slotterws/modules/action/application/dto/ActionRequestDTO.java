package com.isanz.slotterws.modules.action.application.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ActionRequestDTO {
    String name;
    String description;
    List<UUID> roleIds;
}
