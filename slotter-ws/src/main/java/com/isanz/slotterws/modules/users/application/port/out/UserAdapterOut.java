package com.isanz.slotterws.modules.users.application.port.out;

import com.isanz.slotterws.modules.company.application.port.out.CompanyAdapterOut;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.modules.users.domain.UserRepository;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.ExistenceCheckable;
import com.isanz.slotterws.shared.implementations.adapter.Findable;
import com.isanz.slotterws.shared.implementations.adapter.FullViewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserAdapterOut implements FullViewable<UserFullDTO>, Findable<UserResponseDTO, User>, ExistenceCheckable {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyAdapterOut companyAdapterOut;

    @Autowired
    public UserAdapterOut(@Lazy UserRepository userRepository, @Lazy UserMapper userMapper, CompanyAdapterOut companyAdapterOut) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.companyAdapterOut = companyAdapterOut;
    }

    @Override
    public List<UserFullDTO> findAllFull() {
        return userMapper.toFullDTOs(userRepository.findAll());
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

    public List<UserResponseDTO> findAllByCompany(UUID uuid) throws CompanyNotFoundException {

        Company company = companyAdapterOut.findOne(uuid);

        return userMapper.toDTOs(userRepository.findByCompany(company));
    }
}
