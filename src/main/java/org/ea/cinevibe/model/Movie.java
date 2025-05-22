package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.ea.cinevibe.model.enums.MovieStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "movies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    String synopsis;

    @JdbcTypeCode(SqlTypes.JSON)
    Set<Long> languageIds; // create language table

    BigDecimal budget;

    BigDecimal income;

    @Column(name = "trailer_link")
    String trailerLink;

    @Column(name = "release_year")
    Integer releaseYear;

    @Enumerated(EnumType.STRING)
    MovieStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "staff_ids")
    Set<Long> staffIds;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "genre_ids")
    Set<Long> genreIds;

    @Column(name = "poster_image_url")
    String posterImageUrl;

    Double IMDB;

    @Column(name = "average_rating")
    Double averageRating;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "user_ids_for_raitings")
    Set<Long> userIdsForRatings;

    @Column(name = "review_count")
    Integer reviewCount;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }
}
