package com.isanz.slotterws.modules.action.application.port.mapper;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.role.application.port.mapper.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface ActionMapper {

    ActionResponseDTO toDTO(Action entity);

    List<ActionResponseDTO> toDTOs(List<Action> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Action fromDTO(ActionRequestDTO request);

    @Mapping(target = "roles", source = "roles")
    ActionFullDTO toFullDTO(Action entity);

    List<ActionFullDTO> toFullDTOs(List<Action> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(ActionRequestDTO request, @MappingTarget Action action);
}