package com.isanz.slotterws.shared.implementations.adapter;

import java.util.List;
import java.util.UUID;

public interface FullViewable<Full> {
    Full show(UUID uuid);

    List<Full> findAllFull();
}