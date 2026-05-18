package com.isanz.slotterws.modules.action.application.port.in;

import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.domain.Action;
import com.isanz.slotterws.modules.action.domain.ActionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActionAdapterIn {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;

    public ActionAdapterIn(ActionMapper actionMapper, ActionRepository actionRepository) {
        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
    }

    public ActionResponseDTO create(Action action) {
        action = actionRepository.save(action);
        return actionMapper.toDTO(action);
    }

    public void delete(UUID uuid) {
        actionRepository.deleteById(uuid);
    }

    public void update(Action entity) {
        actionRepository.save(entity);
    }
}
