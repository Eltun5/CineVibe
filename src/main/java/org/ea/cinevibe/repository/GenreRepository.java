package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM genres g WHERE g.name % :name", nativeQuery = true)
    List<Genre> getGenresByName(@Param("name") String name);

    @Query("from genres where name in :names")
    List<Genre> getGenresByNames(List<String> names);
}
