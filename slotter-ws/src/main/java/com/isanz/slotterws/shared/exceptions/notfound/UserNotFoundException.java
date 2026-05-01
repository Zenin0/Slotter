package com.isanz.slotterws.shared.exceptions.notfound;

import com.isanz.slotterws.shared.exceptions.base.SlotterException;

import java.util.UUID;

public class UserNotFoundException extends SlotterException {

    public UserNotFoundException(UUID id) {
        super("user not found with ID " + id);
    }

    public UserNotFoundException(String email) {
        super("User not found with email " + email);
    }
}