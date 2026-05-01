package com.isanz.slotterws.shared.implementations.adapter.out;

import java.util.List;
import java.util.UUID;

public interface AdapterOut<Res, E, Full> {

    List<Res> findAll();

    E findOne(UUID id);

    boolean alreadyExists(String parameter);

    Full show(UUID uuid);



}