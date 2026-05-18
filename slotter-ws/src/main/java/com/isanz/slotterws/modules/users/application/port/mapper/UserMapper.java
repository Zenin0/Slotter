package com.isanz.slotterws.modules.users.application.port.mapper;

import com.isanz.slotterws.modules.company.application.port.mapper.CompanyMapper;
import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    User fromDTO(UserRequestDTO request);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(UserRequestDTO request, @MappingTarget User user);

    @Mapping(target = "companyId", source = "company.id")
    UserResponseDTO toDTO(User user);

    List<UserResponseDTO> toDTOs(List<User> users);

    @Mapping(target = "company", source = "company")
    UserFullDTO toFullDTO(User user);

    List<UserFullDTO> toFullDTOs(List<User> users);
}
