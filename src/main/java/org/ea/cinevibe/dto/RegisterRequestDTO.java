package org.ea.cinevibe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(@NotBlank String username,
                                 @Size(min = 8) String password,
                                 @Email String email) {
}
