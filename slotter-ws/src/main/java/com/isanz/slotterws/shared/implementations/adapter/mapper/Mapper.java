package com.isanz.slotterws.shared.implementations.adapter.mapper;

import java.util.List;
import java.util.UUID;

public interface Mapper<E, Req, Res, Full> {

    Res toDTO(E entity);

    List<Res> toDTOs(List<E> entities);

    E fromDTO(Req request);

    Full toFullDTO(E entity);

    List<Full> toFullDTOs(List<E> entities);

    E toEntity(Req request, UUID uuid);



}