package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.enums.MovieStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query(value = "SELECT m.* FROM movies m where m.title % :title ", nativeQuery = true)
    List<Movie> searchByTitle(@Param("title") String title, Pageable page);

}
