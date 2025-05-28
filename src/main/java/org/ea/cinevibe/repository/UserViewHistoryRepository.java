package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.UserViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewHistoryRepository extends JpaRepository<UserViewHistory, Long> {
}
