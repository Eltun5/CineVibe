package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query(value = "SELECT * FROM languages where name % :name", nativeQuery = true)
    List<Language> searchByName(String name);
}
