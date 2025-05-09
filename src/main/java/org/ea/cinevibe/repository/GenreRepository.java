package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("from genres where name ilike '%' || :name || '%'")
    List<Genre> getGenresByName(String name);

    @Query("from genres where name in :names")
    List<Genre> getGenresByNames(List<String> names);
}
