package com.isanz.slotterws.modules.company.application.port.in;

import com.isanz.slotterws.modules.company.application.dto.CompanyRequestDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.application.port.mapper.CompanyMapper;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.company.domain.CompanyRepository;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CompanyAdapterIn implements AdapterIn<CompanyRequestDTO, CompanyResponseDTO> {

    private final CompanyMapper companyMapper;

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyAdapterIn(CompanyMapper companyMapper, CompanyRepository companyRepository) {
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyResponseDTO create(CompanyRequestDTO request) {

        Company company = companyMapper.fromDTO(request);

        company = companyRepository.save(company);

        return companyMapper.toDTO(company);
    }

    @Override
    public void delete(UUID uuid) {
        companyRepository.deleteById(uuid);
    }
}