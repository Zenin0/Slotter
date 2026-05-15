package com.isanz.slotterws.shared.exceptions.alreadyexists;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class CustomerAlreadyExistsException extends SlotterException {

    public CustomerAlreadyExistsException(String message) {
        super("Customer " + message + " does already exists");
    }
}
