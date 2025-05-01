package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m from movies m join m.genres g " +
           "where g in :genres " +
           "and m.title ilike '%' || :title || '%' " +
           "and m.releaseYear = :releaseYear")
    List<Movie> getMoviesByTitleAndGenresAndReleaseYear(String title,
                                                        int releaseYear,
                                                        List<Genre> genres,
                                                        Pageable page);

    @Query("select m from movies m join m.genres g " +
           "where g in :genres " +
           "and m.title ilike '%' || :title || '%'")
    List<Movie> getMoviesByTitleAndGenres(String title,
                                          List<Genre> genres,
                                          Pageable page);
}
