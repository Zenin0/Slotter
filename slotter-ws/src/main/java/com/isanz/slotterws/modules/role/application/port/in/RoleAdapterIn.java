package com.isanz.slotterws.modules.role.application.port.in;

import com.isanz.slotterws.modules.role.application.dto.RoleRequestDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.port.mapper.RoleMapper;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.modules.role.domain.RoleRepository;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleAdapterIn implements AdapterIn<RoleRequestDTO, RoleResponseDTO, Role> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleAdapterIn(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponseDTO create(RoleRequestDTO request) {
        Role role = roleMapper.fromDTO(request);

        role = roleRepository.save(role);

        return roleMapper.toDTO(role);
    }

    @Override
    public void delete(UUID uuid) {
        roleRepository.deleteById(uuid);
    }


    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }
}
