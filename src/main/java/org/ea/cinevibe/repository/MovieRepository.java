package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("from movies where title ilike '%' || ? || '%'")
    List<Movie> getMovieByTitle(String title);

    List<Movie> getMovieByReleaseYear(Integer releaseYear);
}
