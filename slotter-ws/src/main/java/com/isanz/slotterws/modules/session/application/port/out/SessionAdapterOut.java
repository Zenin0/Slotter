package com.isanz.slotterws.modules.session.application.port.out;

import com.isanz.slotterws.modules.session.application.dto.SessionFullDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.domain.Session;
import com.isanz.slotterws.modules.session.domain.SessionRepository;
import com.isanz.slotterws.shared.exceptions.auth.InvalidSessionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SessionAdapterOut {
    private final SessionRepository sessionRepository;

    public SessionAdapterOut(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionFullDTO> findAllFull() {
        return List.of();
    }

    public List<SessionResponseDTO> findAll() {
        return List.of();
    }

    public Session findOne(UUID id) {
        Optional<Session> session = sessionRepository.findById(id);

        if (session.isEmpty()) {
            throw new InvalidSessionException("Invalid session, session not found");
        }

        return session.get();
    }

    public boolean alreadyExists(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);

        return session.isPresent();
    }

    public SessionFullDTO show(UUID uuid) {
        return null;
    }

    public Session findByToken(String token) throws InvalidSessionException {

        Optional<Session> session = sessionRepository.findByToken(token);

        if (session.isEmpty()) {
            throw new InvalidSessionException("Invalid session, session not found");
        }

        return session.get();
    }
}
