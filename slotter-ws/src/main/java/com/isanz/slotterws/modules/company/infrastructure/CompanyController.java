package com.isanz.slotterws.modules.company.infrastructure;

import com.isanz.slotterws.modules.company.application.dto.CompanyFullDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyRequestDTO;
import com.isanz.slotterws.modules.company.application.dto.CompanyResponseDTO;
import com.isanz.slotterws.modules.company.application.service.CompanyService;
import com.isanz.slotterws.shared.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(companyService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> create(@RequestBody CompanyRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(companyService.create(request)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CompanyFullDTO>> show(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(companyService.show(uuid)));
    }

}
