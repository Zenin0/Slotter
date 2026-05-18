package com.isanz.slotterws.shared.implementations.adapter;

public interface Creatable<Entity, Response> {
    Response create(Entity request);
}