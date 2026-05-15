package com.isanz.slotterws.modules.action.infrastructure;

import com.isanz.slotterws.modules.action.application.dto.ActionFullDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionRequestDTO;
import com.isanz.slotterws.modules.action.application.dto.ActionResponseDTO;
import com.isanz.slotterws.modules.action.application.service.ActionService;
import com.isanz.slotterws.shared.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/action")
public class ActionController {


    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActionFullDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(actionService.getAllFull()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ActionResponseDTO>> create(@RequestBody ActionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(actionService.create(request)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ActionFullDTO>> show(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ApiResponse.ok(actionService.show(uuid)));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ActionResponseDTO>> update(@PathVariable UUID uuid, @RequestBody ActionRequestDTO request) {
        actionService.update(uuid, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
