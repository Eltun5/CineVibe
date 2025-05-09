package org.ea.cinevibe.dto;

import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.model.Movie;

import java.util.List;

public record MovieResponseDTO(List<Movie> movies,
                               List<Genre> allGenres,
                               Integer selectedReleaseYear,
                               List<Genre> selectedGenres,
                               boolean sortByTitle,
                               boolean sortByReleaseYear) {
}
