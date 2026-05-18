package com.isanz.slotterws.modules.company.application.port.mapper;

import com.isanz.slotterws.modules.company.application.dto.CompanyFullDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyRequestDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.users.application.port.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyMapper {

    CompanyResponseDTO toDTO(Company company);

    List<CompanyResponseDTO> toDTOs(List<Company> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyLogo", ignore = true)
    @Mapping(target = "users", ignore = true)
    Company fromDTO(CompanyRequestDTO request);

    @Mapping(target = "users", source = "users")
    CompanyFullDTO toFullDTO(Company entity);

    List<CompanyFullDTO> toFullDTOs(List<Company> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyLogo", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateEntity(CompanyRequestDTO request, @MappingTarget Company company);
}