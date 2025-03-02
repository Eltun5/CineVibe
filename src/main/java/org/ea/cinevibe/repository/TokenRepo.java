package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
}
