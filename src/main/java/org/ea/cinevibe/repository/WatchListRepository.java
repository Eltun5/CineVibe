package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.User;
import org.ea.cinevibe.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    Optional<WatchList> getWatchListByUser(User user);
}
