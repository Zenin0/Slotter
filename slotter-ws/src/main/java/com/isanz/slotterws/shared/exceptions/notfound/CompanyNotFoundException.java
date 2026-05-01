package com.isanz.slotterws.shared.exceptions.notfound;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

import java.util.UUID;

public class CompanyNotFoundException extends SlotterException {

    public CompanyNotFoundException(UUID id) {
        super("Company not found: " + id);
    }
}