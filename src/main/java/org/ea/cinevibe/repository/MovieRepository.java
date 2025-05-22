package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT m.* FROM movies m where m.title % :title ", nativeQuery = true)
    List<Movie> searchByTitle(@Param("title") String title, Pageable page);

    @Query(value = "SELECT m.* FROM movies m " +
                   "JOIN movies_genres mg ON m.id = mg.movies_id " +
                   "JOIN genres g ON g.id = mg.genres_id " +
                   "WHERE g.id IN (:genreIds) " +
                   "AND m.release_year = :releaseYear",
            nativeQuery = true)
    List<Movie> getMoviesByGenresAndReleaseYear(@Param("releaseYear") int releaseYear,
                                                @Param("genreIds") List<Long> genres,
                                                Pageable page);

    @Query(value = "SELECT m.* FROM movies m " +
                   "JOIN movies_genres mg ON m.id = mg.movies_id " +
                   "JOIN genres g ON g.id = mg.genres_id " +
                   "WHERE g.id IN (:genreIds) ",
            nativeQuery = true)
    List<Movie> getMoviesByGenres(@Param("genreIds") List<Long> genres,
                                  Pageable page);
}
