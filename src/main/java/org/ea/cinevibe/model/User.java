package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ea.cinevibe.model.enums.UserRole;
import org.ea.cinevibe.security.model.CustomUserDetails;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @PrePersist
    private void prePersist(){
        createdAt=updatedAt=LocalDateTime.now();
        isAccountNonExpired=
                isAccountNonLocked=
                isCredentialsNonExpired=
                        isEnabled=
                                true;
    }

    public CustomUserDetails getCustomUserDetails(){
        return new CustomUserDetails(this);
    }
}
