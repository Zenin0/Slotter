package com.isanz.slotterws.modules.company.application.service;

import com.isanz.slotterws.modules.company.application.dto.CompanyFullDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyRequestDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.application.port.in.CompanyAdapterIn;
import com.isanz.slotterws.modules.company.application.port.out.CompanyAdapterOut;
import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.shared.exceptions.alreadyexists.CompanyAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyAdapterOut companyAdapterOut;
    private final CompanyAdapterIn companyAdapterIn;

    @Autowired
    public CompanyService(CompanyAdapterOut companyAdapterOut, CompanyAdapterIn companyAdapterIn) {
        this.companyAdapterOut = companyAdapterOut;
        this.companyAdapterIn = companyAdapterIn;
    }

    public List<CompanyResponseDTO> getAll() {
        return companyAdapterOut.findAll();
    }

    public CompanyResponseDTO create(CompanyRequestDTO request) throws CompanyAlreadyExistsException {

        if (companyAdapterOut.alreadyExists(request.getName())) {
            throw new CompanyAlreadyExistsException("Company already exists with name: " + request.getName());
        }

        return companyAdapterIn.create(request);
    }


    public Company findOne(UUID companyId) throws CompanyNotFoundException {
        return companyAdapterOut.findOne(companyId);
    }

    public CompanyFullDTO show(UUID uuid) {
        return companyAdapterOut.show(uuid);
    }
}
