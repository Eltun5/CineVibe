package org.ea.cinevibe.specification;

import jakarta.persistence.criteria.Expression;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.enums.MovieStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class MovieSpecification {
    public static Specification<Movie> hasGenre(List<Long> genreIds) {
        return (root, query, criteriaBuilder) -> root.get("genres").get("id").in(genreIds);
    }

    public static Specification<Movie> hasLanguage(List<Long> languageIds) {
        return (root, query, criteriaBuilder) -> root.get("languages").get("id").in(languageIds);
    }

    public static Specification<Movie> hasStatus(List<MovieStatus> statuses) {
        return (root, query, criteriaBuilder) -> root.get("status").in(statuses);
    }

    public static Specification<Movie> imdbGraterThan(Double imdb) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("IMDB"), imdb);
    }

    public static Specification<Movie> equalReleaseYear(Integer releaseYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("releaseYear"), releaseYear);
    }

    public static Specification<Movie> searchTitle(String title) {
        return (root, query, criteriaBuilder) ->
        {
            Expression<Double> function = criteriaBuilder.function(
                    "similarity", Double.class,
                    root.get("title"), criteriaBuilder.literal(title));
            query.orderBy(criteriaBuilder.desc(function));
            return criteriaBuilder.greaterThanOrEqualTo(
                    function,
                    0.3);
        };
    }
}
