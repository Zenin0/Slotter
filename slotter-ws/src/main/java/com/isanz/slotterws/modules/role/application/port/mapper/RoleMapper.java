package com.isanz.slotterws.modules.role.application.port.mapper;

import com.isanz.slotterws.modules.role.application.dto.RoleFullDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleRequestDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RoleMapper {
    RoleResponseDTO toDTO(Role entity);

    List<RoleResponseDTO> toDTOs(List<Role> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "actions", ignore = true)
    Role fromDTO(RoleRequestDTO request);

    @Mapping(target = "users", source = "users")
    @Mapping(target = "actions", source = "actions")
    RoleFullDTO toFullDTO(Role entity);

    List<RoleFullDTO> toFullDTOs(List<Role> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "actions", ignore = true)
    void updateEntity(RoleRequestDTO request, @MappingTarget Role role);
}