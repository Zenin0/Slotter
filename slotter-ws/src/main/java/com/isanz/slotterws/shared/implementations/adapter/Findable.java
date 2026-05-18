package com.isanz.slotterws.shared.implementations.adapter;

import java.util.List;
import java.util.UUID;

public interface Findable<Response, Entity> {
    Entity findOne(UUID id);

    List<Response> findAll();
}