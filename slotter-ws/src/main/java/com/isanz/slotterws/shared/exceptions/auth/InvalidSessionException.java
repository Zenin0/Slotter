package com.isanz.slotterws.shared.exceptions.auth;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class InvalidSessionException extends SlotterException {
    public InvalidSessionException(String message) {
        super(message);
    }
}
