package com.isanz.slotterws.modules.role.application.port.mapper;

import com.isanz.slotterws.modules.role.application.dto.RoleFullDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleRequestDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.port.out.RoleAdapterOut;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RoleMapper implements Mapper<Role, RoleRequestDTO, RoleResponseDTO, RoleFullDTO> {

    private final UserMapper userMapper;
    private final RoleAdapterOut roleAdapterOut;

    public RoleMapper(UserMapper userMapper,@Lazy RoleAdapterOut roleAdapterOut) {
        this.userMapper = userMapper;
        this.roleAdapterOut = roleAdapterOut;
    }

    @Override
    public RoleResponseDTO toDTO(Role entity) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIsActive(entity.getIsActive());

        return dto;
    }

    @Override
    public List<RoleResponseDTO> toDTOs(List<Role> entities) {
        List<RoleResponseDTO> dtos = new ArrayList<>();
        for (Role role : entities) {
            dtos.add(toDTO(role));
        }

        return dtos;
    }

    @Override
    public Role fromDTO(RoleRequestDTO request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setIsActive(true);

        return role;
    }

    @Override
    public RoleFullDTO toFullDTO(Role entity) {
        RoleFullDTO dto = new RoleFullDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIsActive(entity.getIsActive());
        dto.setUsers(userMapper.toDTOs(new ArrayList<>(entity.getUsers())));

        return dto;
    }

    @Override
    public List<RoleFullDTO> toFullDTOs(List<Role> entities) {
        List<RoleFullDTO> dtos = new ArrayList<>();
        for (Role role : entities) {
            dtos.add(toFullDTO(role));
        }

        return dtos;
    }

    @Override
    public Role toEntity(RoleRequestDTO request, UUID uuid) {
        Role role = roleAdapterOut.findOne(uuid);
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setIsActive(request.getIsActive());
        return role;
    }
}
