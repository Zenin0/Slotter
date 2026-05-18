package com.isanz.slotterws.modules.action.application.port.out;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.action.domain.ActionRepository;
import com.isanz.slotterws.shared.exceptions.notfound.ActionNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ActionAdapterOut {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;

    public ActionAdapterOut(ActionMapper actionMapper, ActionRepository actionRepository) {
        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
    }

    public List<ActionFullDTO> findAllFull() {
        return actionMapper.toFullDTOs(actionRepository.findAll());
    }

    public List<ActionResponseDTO> findAll() {
        return actionMapper.toDTOs(actionRepository.findAll());
    }

    public Action findOne(UUID id) {
        Optional<Action> action = actionRepository.findById(id);

        if (action.isEmpty()) {
            throw new ActionNotFoundException(id);
        }

        return action.get();
    }

    public boolean alreadyExists(String parameter) {
        Optional<Action> action = actionRepository.findByName(parameter);

        return action.isEmpty();
    }

    public ActionFullDTO show(UUID uuid) {

        Optional<Action> action = actionRepository.findById(uuid);

        if (action.isEmpty()) {
            throw new ActionNotFoundException(uuid);
        }

        return actionMapper.toFullDTO(action.get());
    }
}
