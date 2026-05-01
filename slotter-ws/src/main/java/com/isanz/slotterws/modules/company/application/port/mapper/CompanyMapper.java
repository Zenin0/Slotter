package com.isanz.slotterws.modules.company.application.port.mapper;

import com.isanz.slotterws.modules.company.application.dto.CompanyFullDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyRequestDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper implements Mapper<Company, CompanyRequestDTO, CompanyResponseDTO, CompanyFullDTO> {

    @Override
    public CompanyResponseDTO toDTO(Company company) {
        CompanyResponseDTO dto = new CompanyResponseDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setSlug(company.getSlug());
        return dto;
    }

    @Override
    public List<CompanyResponseDTO> toDTOs(List<Company> entities) {
        List<CompanyResponseDTO> list = new ArrayList<>();
        for (Company company : entities) {
            list.add(toDTO(company));
        }

        return list;
    }


    @Override
    public Company fromDTO(CompanyRequestDTO request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setSlug(request.getSlug());
        return company;
    }

    @Override
    public CompanyFullDTO toFullDTO(Company entity) {
        CompanyFullDTO dto = new CompanyFullDTO();
        dto.setId(entity.getId());
        dto.setCompanyLogo(entity.getCompanyLogo());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        return dto;
    }
}