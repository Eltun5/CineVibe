package org.ea.cinevibe.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_revoked")
    private boolean isRevoked;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Token(String value, boolean isExpired, boolean isRevoked, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.value = value;
        this.isExpired = isExpired;
        this.isRevoked = isRevoked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
