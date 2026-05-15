package com.isanz.slotterws.modules.action.application.port.out;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.action.domain.ActionRepository;
import com.isanz.slotterws.shared.exceptions.notfound.ActionNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.out.AdapterOut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ActionAdapterOut implements AdapterOut<ActionResponseDTO, Action, ActionFullDTO> {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;

    public ActionAdapterOut(ActionMapper actionMapper, ActionRepository actionRepository) {
        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
    }

    @Override
    public List<ActionFullDTO> findAllFull() {
        return actionMapper.toFullDTOs(actionRepository.findAll());
    }

    @Override
    public List<ActionResponseDTO> findAll() {
        return actionMapper.toDTOs(actionRepository.findAll());
    }

    @Override
    public Action findOne(UUID id) {
        Optional<Action> action = actionRepository.findById(id);

        if (action.isEmpty()) {
            throw new ActionNotFoundException(id);
        }

        return action.get();
    }

    @Override
    public boolean alreadyExists(String parameter) {
        Optional<Action> action = actionRepository.findByName(parameter);

        return action.isEmpty();
    }

    @Override
    public ActionFullDTO show(UUID uuid) {

        Optional<Action> action = actionRepository.findById(uuid);

        if (action.isEmpty()) {
            throw new ActionNotFoundException(uuid);
        }

        return actionMapper.toFullDTO(action.get());
    }
}
