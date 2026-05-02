package com.isanz.slotterws.modules.users.application.port.mapper;

import com.isanz.slotterws.modules.company.application.port.mapper.CompanyMapper;
import com.isanz.slotterws.modules.company.application.service.CompanyService;
import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements Mapper<User, UserRequestDTO, UserResponseDTO, UserFullDTO> {

    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyMapper companyMapper;

    public UserMapper(CompanyService companyService, PasswordEncoder passwordEncoder, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
        this.companyMapper = companyMapper;
    }

    @Override
    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());
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
            user.setCompany(companyService.findOne(request.getCompanyId()));
            user.setIsActive(request.isActive());
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
}