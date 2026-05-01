package com.isanz.slotterws.modules.users.application.service;

import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.in.UserAdapterIn;
import com.isanz.slotterws.modules.users.application.port.out.UserAdapterOut;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.alreadyexists.UserAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserAdapterOut userAdapterOut;
    private final UserAdapterIn userAdapterIn;

    @Autowired
    public UserService(UserAdapterOut userAdapterOut, UserAdapterIn userAdapterIn) {
        this.userAdapterOut = userAdapterOut;
        this.userAdapterIn = userAdapterIn;
    }

    public List<UserResponseDTO> getAll() {
        return userAdapterOut.findAll();
    }

    public UserResponseDTO create(UserRequestDTO request) throws CompanyNotFoundException, GenericException, UserAlreadyExistsException {

        if (userAdapterOut.alreadyExists(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        setAsActive(request);

        return userAdapterIn.create(request);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userAdapterOut.findByEmail(email);
    }

    private void setAsActive(UserRequestDTO request) {
        request.setActive(true);
    }

    public UserFullDTO show(UUID uuid) {
        return userAdapterOut.show(uuid);
    }
}
