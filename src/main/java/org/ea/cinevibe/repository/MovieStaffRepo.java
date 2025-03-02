package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.MovieStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStaffRepo extends JpaRepository<MovieStaff, Long> {
}
