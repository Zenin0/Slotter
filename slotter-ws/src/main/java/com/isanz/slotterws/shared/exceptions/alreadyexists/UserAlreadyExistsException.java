package com.isanz.slotterws.shared.exceptions.alreadyexists;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class UserAlreadyExistsException extends SlotterException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
