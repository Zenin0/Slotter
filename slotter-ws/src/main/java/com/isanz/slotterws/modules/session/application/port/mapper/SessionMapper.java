package com.isanz.slotterws.modules.session.application.port.mapper;

import com.isanz.slotterws.modules.session.application.dto.SessionFullDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionRequestDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.domain.Session;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SessionMapper implements Mapper<Session, SessionRequestDTO, SessionResponseDTO, SessionFullDTO> {

    @Override
    public SessionResponseDTO toDTO(Session entity) {
        SessionResponseDTO response = new SessionResponseDTO();
        response.setToken(entity.getToken());
        response.setUserid(entity.getUser().getId());
        return response;
    }

    @Override
    public List<SessionResponseDTO> toDTOs(List<Session> entities) {
        return List.of();
    }

    @Override
    public Session fromDTO(SessionRequestDTO request) {
        Session session = new Session();
        session.setUser(request.getUser());
        session.setToken(request.getToken());
        session.setIpAddress(request.getIpAddress());
        session.setUserAgent(request.getUserAgent());
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        return session;
    }

    @Override
    public SessionFullDTO toFullDTO(Session entity) {
        SessionFullDTO dto = new SessionFullDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setToken(entity.getToken());
        dto.setIpAddress(entity.getIpAddress());
        dto.setExpiresAt(entity.getExpiresAt());
        return dto;
    }

    @Override
    public List<SessionFullDTO> toFullDTOs(List<Session> entities) {
        List<SessionFullDTO> dtos = new ArrayList<>();
        for (Session session : entities) {
            dtos.add(toFullDTO(session));
        }

        return dtos;
    }

    @Override
    public Session toEntity(SessionRequestDTO request, UUID uuid) {
        Session session = new Session();
        session.setId(uuid);
        session.setToken(request.getToken());
        session.setIpAddress(request.getIpAddress());
        session.setUserAgent(request.getUserAgent());
        session.setUser(request.getUser());

        return session;
    }
}
