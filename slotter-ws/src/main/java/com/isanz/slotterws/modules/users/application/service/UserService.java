package com.isanz.slotterws.modules.users.application.service;

import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.in.UserAdapterIn;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import com.isanz.slotterws.modules.users.application.port.out.UserAdapterOut;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.alreadyexists.UserAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserAdapterOut userAdapterOut;
    private final UserAdapterIn userAdapterIn;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public UserService(UserAdapterOut userAdapterOut, UserAdapterIn userAdapterIn, UserMapper userMapper, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.userAdapterOut = userAdapterOut;
        this.userAdapterIn = userAdapterIn;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    public List<UserResponseDTO> getAll() {
        return userAdapterOut.findAll();
    }

    public UserResponseDTO create(UserRequestDTO request) throws CompanyNotFoundException, GenericException, UserAlreadyExistsException {
        if (userAdapterOut.alreadyExists(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }
        User user = userMapper.fromDTO(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompany(entityManager.getReference(Company.class, request.getCompanyId()));
        user.setIsActive(true);
        return userAdapterIn.create(user);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userAdapterOut.findByEmail(email);
    }

    public UserFullDTO show(UUID uuid) {
        return userAdapterOut.show(uuid);
    }

    public List<UserResponseDTO> findAllByCompany(UUID uuid) throws CompanyNotFoundException {
        return userAdapterOut.findAllByCompany(uuid);
    }

    public void update(UUID uuid, UserRequestDTO request) {
        User existing = userAdapterOut.findOne(uuid);
        userMapper.updateEntity(request, existing);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getCompanyId() != null) {
            existing.setCompany(entityManager.getReference(Company.class, request.getCompanyId()));
        }
        userAdapterIn.update(existing);
    }
}