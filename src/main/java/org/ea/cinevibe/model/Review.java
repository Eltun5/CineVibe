package org.ea.cinevibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Movie movie;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @Size(min = 1, max = 5)
    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_contains_spoiler")
    private boolean isContainsSpoiler;

    @Column(name = "helpful_count")
    private Long helpfulCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }
}
