package com.isanz.slotterws.shared.implementations.adapter.in;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface AdapterIn<Req, Res> {

    Res create(Req request);

    void delete(UUID uuid);

}