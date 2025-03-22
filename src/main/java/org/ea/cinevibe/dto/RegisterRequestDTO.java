package org.ea.cinevibe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String username,
                                 @Min(8) String password,
                                 @Email String email) {
}
