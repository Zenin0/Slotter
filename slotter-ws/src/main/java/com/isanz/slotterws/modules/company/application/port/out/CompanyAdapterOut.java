package com.isanz.slotterws.modules.company.application.port.out;

import com.isanz.slotterws.modules.company.application.dto.CompanyFullDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.application.port.mapper.CompanyMapper;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.company.domain.CompanyRepository;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.out.AdapterOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CompanyAdapterOut implements AdapterOut<CompanyResponseDTO, Company, CompanyFullDTO> {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyAdapterOut(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    @Override
    public List<CompanyResponseDTO> findAll() {
        return companyMapper.toDTOs(companyRepository.findAll());
    }

    @Override
    public Company findOne(UUID companyId) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findById(companyId);

        if (company.isEmpty()) {
            throw new CompanyNotFoundException(companyId);
        }

        return company.get();
    }

    @Override
    public boolean alreadyExists(String companyName) {
        Optional<Company> company = companyRepository.findByName((companyName));

        return company.isPresent();
    }

    @Override
    public CompanyFullDTO show(UUID uuid) {
        Company company = findOne(uuid);

        return companyMapper.toFullDTO(company);
    }

}
