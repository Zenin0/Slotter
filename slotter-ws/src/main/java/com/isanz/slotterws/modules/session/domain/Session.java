package com.isanz.slotterws.modules.session.domain;

import com.isanz.slotterws.modules.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true)
    private String token;

    private String ipAddress;
    private String userAgent;

    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}