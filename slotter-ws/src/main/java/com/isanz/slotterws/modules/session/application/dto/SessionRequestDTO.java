package com.isanz.slotterws.modules.session.application.dto;

import com.isanz.slotterws.modules.users.domain.User;
import lombok.Data;

@Data
public class SessionRequestDTO {
    String email;
    String password;
    String ipAddress;
    String userAgent;
    String token;
    User user;

}
