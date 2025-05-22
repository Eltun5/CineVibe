package org.ea.cinevibe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ea.cinevibe.model.enums.MovieStatus;

import java.math.BigDecimal;
import java.util.Set;

public record MovieRequestDTO(@NotBlank String title,
                              @NotBlank String synopsis,
                              @Min(1850) Integer releaseYear,
                              MovieStatus status,
                              Set<Long> staffIds,
                              Set<Long> genreIds,
                              Set<Long> languageIds,
                              @Min(0) BigDecimal budget,
                              @Min(0) BigDecimal income,
                              String trailerLink,
                              @Min(0) Double IMDB) {
}
