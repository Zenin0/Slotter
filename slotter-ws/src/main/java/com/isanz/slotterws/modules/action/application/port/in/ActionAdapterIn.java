package com.isanz.slotterws.modules.action.application.port.in;

import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.action.domain.ActionRepository;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActionAdapterIn implements AdapterIn<ActionRequestDTO, ActionResponseDTO, Action> {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;

    public ActionAdapterIn(ActionMapper actionMapper, ActionRepository actionRepository) {
        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
    }

    @Override
    public ActionResponseDTO create(ActionRequestDTO request) {
        Action action = actionMapper.fromDTO(request);

        action = actionRepository.save(action);

        return actionMapper.toDTO(action);
    }

    @Override
    public void delete(UUID uuid) {
        actionRepository.deleteById(uuid);
    }

    @Override
    public void update(Action entity) {
        actionRepository.save(entity);
    }
}
