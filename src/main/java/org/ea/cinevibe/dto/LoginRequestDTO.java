package org.ea.cinevibe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String username,
                              @Min(8) String Password) {
}
