package com.isanz.slotterws.modules.users.infrastructure;

import com.isanz.slotterws.modules.users.application.dto.UserFullDTO;
import com.isanz.slotterws.modules.users.application.dto.UserRequestDTO;
import com.isanz.slotterws.modules.users.application.dto.UserResponseDTO;
import com.isanz.slotterws.modules.users.application.service.UserService;
import com.isanz.slotterws.shared.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(userService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.create(request)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<UserFullDTO>> show(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(userService.show(uuid)));
    }

    @GetMapping("/company/{uuid}")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findAllByCompany(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(userService.findAllByCompany(uuid)));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(@PathVariable UUID uuid, @RequestBody UserRequestDTO request ) {
        userService.update(uuid, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
