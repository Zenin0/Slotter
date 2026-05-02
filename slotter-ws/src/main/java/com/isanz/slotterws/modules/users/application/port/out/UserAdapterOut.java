package com.isanz.slotterws.modules.users.application.port.out;

import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.modules.users.domain.UserRepository;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.out.AdapterOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserAdapterOut implements AdapterOut<UserResponseDTO, User, UserFullDTO> {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserAdapterOut(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserResponseDTO> findAll() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public User findOne(UUID userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return user.get();
    }

    @Override
    public boolean alreadyExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }

    @Override
    public UserFullDTO show(UUID uuid) throws UserNotFoundException {

        User user = findOne(uuid);

        return userMapper.toFullDTO(user);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        }

        return user.get();
    }
}
