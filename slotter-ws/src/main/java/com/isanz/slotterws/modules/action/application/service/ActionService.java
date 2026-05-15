package com.isanz.slotterws.modules.action.application.service;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.port.in.ActionAdapterIn;
import com.isanz.slotterws.modules.action.application.port.mapper.ActionMapper;
import com.isanz.slotterws.modules.action.application.port.out.ActionAdapterOut;
import com.isanz.slotterws.modules.action.domain.Action;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActionService {


    private final ActionAdapterOut actionAdapterOut;
    private final ActionAdapterIn actionAdapterIn;
    private final ActionMapper actionMapper;

    public ActionService(ActionAdapterOut actionAdapterOut, ActionAdapterIn actionAdapterIn, ActionMapper actionMapper) {
        this.actionAdapterOut = actionAdapterOut;
        this.actionAdapterIn = actionAdapterIn;
        this.actionMapper = actionMapper;
    }

    public List<ActionFullDTO> getAllFull() {
        return actionAdapterOut.findAllFull();
    }

    public List<ActionResponseDTO> getAll() {
        return actionAdapterOut.findAll();
    }

    public ActionResponseDTO create(ActionRequestDTO request) {
        return actionAdapterIn.create(request);
    }

    public ActionFullDTO show(UUID uuid) {
        return actionAdapterOut.show(uuid);
    }

    public void update(UUID uuid, ActionRequestDTO request) {
        Action action = actionMapper.toEntity(request, uuid);
        actionAdapterIn.update(action);
    }
}
