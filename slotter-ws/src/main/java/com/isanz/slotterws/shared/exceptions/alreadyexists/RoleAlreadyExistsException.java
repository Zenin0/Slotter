package com.isanz.slotterws.shared.exceptions.alreadyexists;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class RoleAlreadyExistsException extends SlotterException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
