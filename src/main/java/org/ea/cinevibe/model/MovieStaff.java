package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ea.cinevibe.model.enums.MovieStaffRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "movie_staffs")
public class MovieStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    private String biography;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieStaffRole role;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }
}
