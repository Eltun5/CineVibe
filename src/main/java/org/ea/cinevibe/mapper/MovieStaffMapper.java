package org.ea.cinevibe.mapper;

import org.ea.cinevibe.model.MovieStaff;

import java.time.LocalDateTime;

public class MovieStaffMapper {
    public static MovieStaff mapMovieStaff(MovieStaff oldMovieStaff, MovieStaff newMovieStaff){
        oldMovieStaff.setBiography(newMovieStaff.getBiography());
        oldMovieStaff.setBirthDay(newMovieStaff.getBirthDay());
        oldMovieStaff.setRole(newMovieStaff.getRole());
        oldMovieStaff.setFirstName(newMovieStaff.getFirstName());
        oldMovieStaff.setLastName(newMovieStaff.getLastName());
        oldMovieStaff.setImageUrl(newMovieStaff.getImageUrl());
        oldMovieStaff.setUpdatedAt(LocalDateTime.now());

        return oldMovieStaff;
    }
}
