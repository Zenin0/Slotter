package com.isanz.slotterws.modules.role.application.port.out;

import com.isanz.slotterws.modules.role.application.dto.RoleFullDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.port.mapper.RoleMapper;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.modules.role.domain.RoleRepository;
import com.isanz.slotterws.modules.users.application.port.out.UserAdapterOut;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.notfound.RoleNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.ExistenceCheckable;
import com.isanz.slotterws.shared.implementations.adapter.Findable;
import com.isanz.slotterws.shared.implementations.adapter.FullViewable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleAdapterOut implements Findable<RoleResponseDTO, Role>, ExistenceCheckable, FullViewable<RoleFullDTO> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserAdapterOut userAdapterOut;

    public RoleAdapterOut(RoleRepository roleRepository, RoleMapper roleMapper, UserAdapterOut userAdapterOut) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userAdapterOut = userAdapterOut;
    }

    @Override
    public List<RoleResponseDTO> findAll() {
        return roleMapper.toDTOs(roleRepository.findAll());
    }

    @Override
    public List<RoleFullDTO> findAllFull() {
        return roleMapper.toFullDTOs(roleRepository.findAll());
    }

    @Override
    public Role findOne(UUID uuid) {
        Optional<Role> role = roleRepository.findById(uuid);

        if (role.isEmpty()) {
            throw new RoleNotFoundException(uuid);
        }

        return role.get();
    }

    @Override
    public boolean alreadyExists(String parameter) {
        Optional<Role> role = roleRepository.findByName(parameter);

        return role.isPresent();

    }

    @Override
    public RoleFullDTO show(UUID uuid) {
        Optional<Role> role = roleRepository.findById(uuid);

        if (role.isEmpty()) {
            throw new RoleNotFoundException(uuid);
        }

        return roleMapper.toFullDTO(role.get());

    }

    public List<RoleFullDTO> findAllUser(UUID uuid) {

        Set<User> users = Collections.singleton(userAdapterOut.findOne(uuid));

        return roleMapper.toFullDTOs(roleRepository.findAllByUsersIn(users));
    }
}
