package org.ea.cinevibe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(@NotBlank String username,
                              @Size(min = 8) String password) {
}
