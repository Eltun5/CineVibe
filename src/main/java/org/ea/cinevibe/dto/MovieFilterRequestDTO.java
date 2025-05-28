package org.ea.cinevibe.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.ea.cinevibe.model.enums.MovieStatus;

import java.util.List;

public record MovieFilterRequestDTO(List<Long> genreIds,
                                    @Min(1850) Integer releaseYear,
                                    @Min(0) @Max(10) Double IMDB,
                                    @Min(1) int pageNum,
                                    List<Long> languageIds,
                                    List<MovieStatus> status,
                                    boolean sortByTitle,
                                    boolean sortByReleaseYear,
                                    boolean sortByImdb,
                                    boolean sortByBudget,
                                    boolean sortByIncome,
                                    boolean sortByReviewCount,
                                    boolean isSortingDesc) {
}
