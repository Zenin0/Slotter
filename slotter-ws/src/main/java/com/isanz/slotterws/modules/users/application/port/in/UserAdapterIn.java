package com.isanz.slotterws.modules.users.application.port.in;

import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.modules.users.domain.UserRepository;
import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAdapterIn {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserAdapterIn(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(User user) throws CompanyNotFoundException, GenericException {
        try {
            user = userRepository.save(user);

            return userMapper.toDTO(user);
        } catch (CompanyNotFoundException | GenericException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GenericException("Ha ", ex);
        }
    }

    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public void update(User entity) {
        userRepository.save(entity);
    }

}