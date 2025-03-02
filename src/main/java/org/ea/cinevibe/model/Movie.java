package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String synopsis;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToMany
    private Set<MovieStaff> staffs;

    @ManyToMany
    private Set<Genre> genres;

    @Column(name = "poster_image_url")
    private String posterImageUrl;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }
}
