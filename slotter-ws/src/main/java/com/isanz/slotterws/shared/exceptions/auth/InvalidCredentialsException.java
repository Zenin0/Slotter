package com.isanz.slotterws.shared.exceptions.auth;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class InvalidCredentialsException extends SlotterException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
