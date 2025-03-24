package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.*;
import org.ea.cinevibe.model.enums.UserRole;
import org.ea.cinevibe.security.model.SecurityUserDetails;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private boolean isEmailVerified;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @PrePersist
    private void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
        isAccountNonExpired =
                isAccountNonLocked =
                        isCredentialsNonExpired =
                                isEnabled =
                                        true;
    }

    public SecurityUserDetails getCustomUserDetails() {
        return new SecurityUserDetails(this);
    }
}
