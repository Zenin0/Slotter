package com.isanz.slotterws.modules.customer.infrastructure;

import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.service.CustomerService;
import com.isanz.slotterws.shared.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAll(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.ok(customerService.getAllBySlug(slug)));
    }

    @PostMapping("/{slug}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> create(@RequestBody CustomerRequestDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.ok(customerService.create(requestDTO)));
    }

    @DeleteMapping("/{slug}/{uuid}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final UUID uuid, @PathVariable final String slug) {
        customerService.delete(uuid);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{slug}/{uuid}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> update(@RequestBody final CustomerRequestDTO request, @PathVariable UUID uuid) {
        customerService.update(request, uuid);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
