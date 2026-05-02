package com.isanz.slotterws.shared.exceptions.notfound;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

import java.util.UUID;

public class RoleNotFoundException extends SlotterException {

    public RoleNotFoundException(UUID id) {
        super("Role not found: " + id);
    }
}