package org.ea.cinevibe.mapper;

import org.ea.cinevibe.dto.MovieRequestDTO;
import org.ea.cinevibe.model.Movie;

import java.time.LocalDateTime;

public class MovieMapper {
    public static Movie toEntity(MovieRequestDTO dto) {
        Movie movie = new Movie();
        setAllParam(dto, movie);
        return movie;
    }

    public static Movie updateEntity(MovieRequestDTO dto, Movie oldMovie) {
        setAllParam(dto, oldMovie);
        oldMovie.setUpdatedAt(java.time.LocalDateTime.now());
        return oldMovie;
    }

    private static void setAllParam(MovieRequestDTO dto, Movie oldMovie) {
        oldMovie.setTitle(dto.title());
        oldMovie.setSynopsis(dto.synopsis());
        oldMovie.setReleaseYear(dto.releaseYear());
        oldMovie.setStatus(dto.status());
        oldMovie.setStaffIds(dto.staffIds());
        oldMovie.setGenreIds(dto.genreIds());
        oldMovie.setLanguageIds(dto.languageIds());
        oldMovie.setBudget(dto.budget());
        oldMovie.setIncome(dto.income());
        oldMovie.setTrailerLink(dto.trailerLink());
        oldMovie.setIMDB(dto.IMDB());
    }
}
