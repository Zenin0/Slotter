package com.isanz.slotterws.modules.action.application.service;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.in.ActionAdapterIn;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.application.port.out.ActionAdapterOut;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.role.domain.Role;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActionService {
    private final ActionAdapterOut actionAdapterOut;
    private final ActionAdapterIn actionAdapterIn;
    private final ActionMapper actionMapper;
    private final EntityManager entityManager;

    public ActionService(ActionAdapterOut actionAdapterOut, ActionAdapterIn actionAdapterIn,
                         ActionMapper actionMapper, EntityManager entityManager) {
        this.actionAdapterOut = actionAdapterOut;
        this.actionAdapterIn = actionAdapterIn;
        this.actionMapper = actionMapper;
        this.entityManager = entityManager;
    }

    public List<ActionFullDTO> getAllFull() {
        return actionAdapterOut.findAllFull();
    }

    public List<ActionResponseDTO> getAll() {
        return actionAdapterOut.findAll();
    }

    public ActionResponseDTO create(ActionRequestDTO request) {
        Action action = actionMapper.fromDTO(request);
        action.setRoles(resolveRoles(request.getRoleIds()));
        return actionAdapterIn.create(action);
    }

    public ActionFullDTO show(UUID uuid) {
        return actionAdapterOut.show(uuid);
    }

    public void update(UUID uuid, ActionRequestDTO request) {
        Action existing = actionAdapterOut.findOne(uuid);
        actionMapper.updateEntity(request, existing);
        existing.setRoles(resolveRoles(request.getRoleIds()));
        actionAdapterIn.update(existing);
    }

    private Set<Role> resolveRoles(Set<UUID> roleIds) {
        return roleIds.stream()
                .map(id -> entityManager.getReference(Role.class, id))
                .collect(Collectors.toSet());
    }
}