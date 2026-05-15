package com.isanz.slotterws.modules.action.application.port.mapper;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.out.ActionAdapterOut;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.port.mapper.RoleMapper;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ActionMapper implements Mapper<Action, ActionRequestDTO, ActionResponseDTO, ActionFullDTO> {

    private final EntityManager entityManager;
    private final RoleMapper roleMapper;
    private final ActionAdapterOut actionAdapterOut;

    public ActionMapper(EntityManager entityManager, @Lazy RoleMapper roleMapper, @Lazy ActionAdapterOut actionAdapterOut) {
        this.entityManager = entityManager;
        this.roleMapper = roleMapper;
        this.actionAdapterOut = actionAdapterOut;
    }

    @Override
    public ActionResponseDTO toDTO(Action entity) {
        ActionResponseDTO dto = new ActionResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    @Override
    public List<ActionResponseDTO> toDTOs(List<Action> entities) {
        List<ActionResponseDTO> dtos = new ArrayList<>();

        for (Action action : entities) {
            dtos.add(toDTO(action));
        }

        return dtos;
    }

    @Override
    public Action fromDTO(ActionRequestDTO request) {
        Action action = new Action();
        action.setName(request.getName());
        action.setDescription(request.getDescription());
        Set<Role> roleList = new HashSet<>();
        for (UUID uuid : request.getRoleIds()) {
            roleList.add(entityManager.getReference(Role.class, uuid));
        }

        action.setRoles(roleList);
        return action;
    }

    @Override
    public ActionFullDTO toFullDTO(Action entity) {
        ActionFullDTO dto = new ActionFullDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        Set<RoleResponseDTO> roleList = new HashSet<>();
        for (Role role : entity.getRoles()) {
            roleList.add(roleMapper.toDTO(role));
        }
        dto.setRoles(roleList);
        return dto;
    }

    @Override
    public List<ActionFullDTO> toFullDTOs(List<Action> entities) {
        List<ActionFullDTO> list = new ArrayList<>();

        for (Action action : entities) {
            list.add(toFullDTO(action));
        }

        return list;
    }

    @Override
    public Action toEntity(ActionRequestDTO request, UUID uuid) {
        Action action = actionAdapterOut.findOne(uuid);
        action.setDescription(request.getDescription());
        action.setName(request.getName());

        Set<Role> roleList = new HashSet<>();
        for (UUID id : request.getRoleIds()) {
            roleList.add(entityManager.getReference(Role.class, id));
        }

        action.setRoles(roleList);

        return action;
    }
}
