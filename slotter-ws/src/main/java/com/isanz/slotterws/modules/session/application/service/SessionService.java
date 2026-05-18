package com.isanz.slotterws.modules.session.application.service;

import com.isanz.slotterws.modules.session.application.dto.SessionRequestDTO;
import com.isanz.slotterws.modules.session.application.dto.SessionResponseDTO;
import com.isanz.slotterws.modules.session.application.port.in.SessionAdapterIn;
import com.isanz.slotterws.modules.session.application.port.out.SessionAdapterOut;
import com.isanz.slotterws.modules.session.domain.Session;
import com.isanz.slotterws.modules.users.application.service.UserService;
import com.isanz.slotterws.modules.users.domain.User;
import com.isanz.slotterws.shared.exceptions.auth.InvalidCredentialsException;
import com.isanz.slotterws.shared.exceptions.auth.InvalidSessionException;
import com.isanz.slotterws.shared.exceptions.auth.SessionExpiredException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class SessionService {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionAdapterIn sessionAdapterIn;
    private final SessionAdapterOut sessionAdapterOut;

    public SessionService(UserService userService, PasswordEncoder passwordEncoder, SessionAdapterIn sessionAdapterIn, SessionAdapterOut sessionAdapterOut) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionAdapterIn = sessionAdapterIn;
        this.sessionAdapterOut = sessionAdapterOut;
    }

    public SessionResponseDTO login(SessionRequestDTO request) throws UserNotFoundException {

        User user = userService.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = UUID.randomUUID().toString();
        request.setToken(token);
        request.setUser(user);
        return sessionAdapterIn.create(request);
    }

    public void logout(SessionRequestDTO request) throws InvalidSessionException {

        Session session = sessionAdapterOut.findByToken(request.getToken());

        sessionAdapterIn.delete(session.getId());
    }

    public User validateToken(String token) throws InvalidSessionException {
        Session session = sessionAdapterOut.findByToken(token);

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SessionExpiredException("Session expired");
        }

        return session.getUser();
    }
}
