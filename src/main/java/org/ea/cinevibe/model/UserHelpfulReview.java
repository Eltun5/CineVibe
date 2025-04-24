package org.ea.cinevibe.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user_helpful_review")
public class UserHelpfulReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviews_id")
    private Review reviews;

    @ManyToOne
    @JoinColumn(name = "users_helpful_id")
    private User users;
}
