package com.isanz.slotterws.modules.session.application.port.mapper;

import com.isanz.slotterws.modules.session.application.dto.SessionFullDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionRequestDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.domain.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "userid", source = "user.id")
    SessionResponseDTO toDTO(Session entity);

    List<SessionResponseDTO> toDTOs(List<Session> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    Session fromDTO(SessionRequestDTO request);

    @Mapping(target = "userId", source = "user.id")
    SessionFullDTO toFullDTO(Session entity);

    List<SessionFullDTO> toFullDTOs(List<Session> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    void updateEntity(SessionRequestDTO request, @MappingTarget Session session);
}