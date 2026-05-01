package com.isanz.slotterws.shared.exceptions.alreadyexists;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

public class CompanyAlreadyExistsException extends SlotterException {

    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
