package me.finnthorben.thingpeoplelist.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record LoginRequest(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password
) {
}
