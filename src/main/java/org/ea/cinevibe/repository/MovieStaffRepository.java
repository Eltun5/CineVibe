package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.MovieStaff;
import org.ea.cinevibe.model.enums.MovieStaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieStaffRepository extends JpaRepository<MovieStaff, Long> {
    @Query("from movie_staffs where firstName || lastName ilike '%' || :name || '%'")
    List<MovieStaff> getMovieStaffsByName(String name);

    List<MovieStaff> getMovieStaffsByRole(MovieStaffRole role);
}
