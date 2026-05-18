package com.isanz.slotterws.modules.session.application.port.in;

import com.isanz.slotterws.modules.session.application.dto.SessionRequestDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.application.port.mapper.SessionMapper;
import com.isanz.slotterws.modules.session.domain.Session;
import com.isanz.slotterws.modules.session.domain.SessionRepository;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SessionAdapterIn implements AdapterIn<SessionRequestDTO, SessionResponseDTO, Session> {

    private final SessionMapper sessionMapper;
    private final SessionRepository sessionRepository;

    public SessionAdapterIn(SessionMapper sessionMapper, SessionRepository sessionRepository) {
        this.sessionMapper = sessionMapper;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public SessionResponseDTO create(SessionRequestDTO request) {
        Session session = sessionMapper.fromDTO(request);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        session = sessionRepository.save(session);
        return sessionMapper.toDTO(session);
    }

    public void delete(UUID uuid) {
        sessionRepository.deleteById(uuid);
    }

    @Override
    public void update(Session entity) {
        sessionRepository.save(entity);
    }
}
