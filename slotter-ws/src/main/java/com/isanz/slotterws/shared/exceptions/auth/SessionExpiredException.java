package com.isanz.slotterws.shared.exceptions.auth;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class SessionExpiredException extends SlotterException {
    public SessionExpiredException(String message) {
        super(message);
    }
}
