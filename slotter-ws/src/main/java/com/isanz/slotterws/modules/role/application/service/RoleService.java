package com.isanz.slotterws.modules.role.application.service;

import com.isanz.slotterws.modules.role.application.dto.RoleFullDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleRequestDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleUpdateDTO;
import com.isanz.slotterws.modules.role.application.port.in.RoleAdapterIn;
import com.isanz.slotterws.modules.role.application.port.mapper.RoleMapper;
import com.isanz.slotterws.modules.role.application.port.out.RoleAdapterOut;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.modules.users.application.port.out.UserAdapterOut;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.alreadyexists.RoleAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleAdapterOut roleAdapterOut;
    private final RoleAdapterIn roleAdapterIn;
    private final UserAdapterOut userAdapterOut;
    private final RoleMapper roleMapper;

    public RoleService(RoleAdapterOut roleAdapterOut, RoleAdapterIn roleAdapterIn, UserAdapterOut userAdapterOut, RoleMapper roleMapper) {
        this.roleAdapterOut = roleAdapterOut;
        this.roleAdapterIn = roleAdapterIn;
        this.userAdapterOut = userAdapterOut;
        this.roleMapper = roleMapper;
    }

    public List<RoleResponseDTO> getAll() {
        return roleAdapterOut.findAll();
    }

    public RoleResponseDTO create(RoleRequestDTO request) {

        if (roleAdapterOut.alreadyExists(request.getName())) {
            throw new RoleAlreadyExistsException("Ya existe un rol con el nombre " + request.getName());
        }

        return roleAdapterIn.create(request);
    }

    public RoleFullDTO show(UUID uuid) {
        return roleAdapterOut.show(uuid);
    }

    public void assignToUser(UUID roleId, UUID userId) {
        Role role = roleAdapterOut.findOne(roleId);
        User user = userAdapterOut.findOne(userId);

        addUserToRole(role, user);

        roleAdapterIn.update(role);

    }

    private void addUserToRole(Role role, User user) {

        Set<User> users = role.getUsers();
        users.add(user);

    }

    public List<RoleResponseDTO> listAllUser(UUID uuid) throws UserNotFoundException {
        return roleAdapterOut.findAllUser(uuid);
    }

    public void update(RoleUpdateDTO request) {

        Role role = roleAdapterOut.findOne(request.getId());
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setIsActive(request.getIsActive());

        roleAdapterIn.update(role);
    }
}
