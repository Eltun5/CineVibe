package org.ea.cinevibe.security.repository;

import org.ea.cinevibe.security.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> getTokenByValue(String value);
}
