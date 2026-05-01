package com.isanz.slotterws.modules.session.infrastructure;

import com.isanz.slotterws.modules.session.application.dto.SessionRequestDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.application.service.SessionService;
import com.isanz.slotterws.shared.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SessionResponseDTO>> login(@RequestBody SessionRequestDTO request, HttpServletRequest http) {

        request.setIpAddress(http.getRemoteAddr());
        request.setUserAgent(http.getHeader("User-Agent"));

        return ResponseEntity.ok(ApiResponse.ok(sessionService.login(request)));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody SessionRequestDTO request, HttpServletRequest http) {

        request.setIpAddress(http.getRemoteAddr());
        request.setUserAgent(http.getHeader("User-Agent"));

        sessionService.logout(request);

        return ResponseEntity.ok(ApiResponse.ok("Logged out successfully"));
    }

}
