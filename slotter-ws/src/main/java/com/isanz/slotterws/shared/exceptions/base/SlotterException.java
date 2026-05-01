package com.isanz.slotterws.shared.exceptions.base;

public abstract class SlotterException extends RuntimeException {

    public SlotterException(String message) {
        super(message);
    }

    public SlotterException(String message, Exception exception) {
        super(message, exception);
    }
}