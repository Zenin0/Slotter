package com.isanz.slotterws.shared.exceptions;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class GenericException extends SlotterException {

    public GenericException(String message, Exception exception) {
        super(message, exception);
    }
}
