package com.isanz.slotterws.modules.users.application.port.mapper;

import com.isanz.slotterws.modules.company.application.port.mapper.CompanyMapper;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.port.out.UserAdapterOut;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapper implements Mapper<User, UserRequestDTO, UserResponseDTO, UserFullDTO> {

    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final CompanyMapper companyMapper;
    private final UserAdapterOut userAdapterOut;

    public UserMapper(PasswordEncoder passwordEncoder, EntityManager entityManager, CompanyMapper companyMapper, @Lazy UserAdapterOut userAdapterOut) {
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.companyMapper = companyMapper;
        this.userAdapterOut = userAdapterOut;
    }

    @Override
    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());
        dto.setCompanyId(user.getCompany().getId());
        return dto;
    }

    @Override
    public List<UserResponseDTO> toDTOs(List<User> entities) {
        List<UserResponseDTO> list = new ArrayList<>();
        for (User user : entities) {
            list.add(toDTO(user));
        }
        return list;
    }


    @Override
    public User fromDTO(UserRequestDTO request) throws CompanyNotFoundException, GenericException {
        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            Company company = entityManager.getReference(Company.class, request.getCompanyId());
            user.setCompany(company);

            user.setIsActive(request.getIsActive());
            return user;
        } catch (CompanyNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GenericException(ex.getMessage(), ex);
        }
    }

    public UserFullDTO toFullDTO(User user) {
        UserFullDTO dto = new UserFullDTO();
        dto.setId(user.getId());
        dto.setCompany(companyMapper.toDTO(user.getCompany()));
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setProfileImage(user.getProfileImage());
        dto.setIsActive(user.getIsActive());
        return dto;
    }

    public List<UserFullDTO> toFullDTOs(List<User> users) {
        List<UserFullDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(toFullDTO(user));
        }

        return dtos;
    }

    @Override
    public User toEntity(UserRequestDTO request, UUID uuid) {
        User existing = userAdapterOut.findOne(uuid);
        existing.setEmail(request.getEmail());
        existing.setUsername(request.getUsername());
        existing.setIsActive(request.getIsActive());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getCompanyId() != null) {
            existing.setCompany(entityManager.getReference(Company.class, request.getCompanyId()));
        }
        return existing;
    }
}