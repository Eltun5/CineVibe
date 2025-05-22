package org.ea.cinevibe.dto;

import java.util.List;

public record MovieFilterRequestDTO(List<Long> genres,
                                    Integer releaseYear,
                                    int pageNum,
                                    boolean sortByTitle,
                                    boolean sortByReleaseYear) {
}
