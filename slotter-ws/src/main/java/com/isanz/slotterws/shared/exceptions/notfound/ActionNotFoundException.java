package com.isanz.slotterws.shared.exceptions.notfound;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

import java.util.UUID;

public class ActionNotFoundException extends SlotterException {

    public ActionNotFoundException(UUID id) {
        super("Action not found: " + id);
    }
}