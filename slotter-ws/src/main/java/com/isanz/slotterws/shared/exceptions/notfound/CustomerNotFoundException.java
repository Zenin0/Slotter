package com.isanz.slotterws.shared.exceptions.notfound;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

import java.util.UUID;

public class CustomerNotFoundException extends SlotterException {

    public CustomerNotFoundException(UUID id) {
        super("Customer not found: " + id);
    }
}