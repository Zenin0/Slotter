package com.isanz.slotterws.modules.role.infrastructure;

import com.isanz.slotterws.modules.role.application.dto.RoleFullDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleRequestDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleResponseDTO;
import com.isanz.slotterws.modules.role.application.dto.RoleUpdateDTO;
import com.isanz.slotterws.modules.role.application.service.RoleService;
import com.isanz.slotterws.modules.role.domain.Role;
import com.isanz.slotterws.shared.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(roleService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(@RequestBody RoleRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(roleService.create(request)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<RoleFullDTO>> show(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(roleService.show(uuid)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> update(@RequestBody RoleUpdateDTO request) {
        roleService.update(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{roleId}/assign/{userId}")
    public ResponseEntity<ApiResponse<String>> assignToUser(@PathVariable UUID roleId, @PathVariable UUID userId) {
        roleService.assignToUser(roleId, userId);
        return ResponseEntity.ok(ApiResponse.ok("Role assigned successfully"));
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> listAllUser(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(roleService.listAllUser(uuid)));
    }
}
