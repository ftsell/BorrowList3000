package me.finnthorben.thingpeoplelist.api.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record LoginResponse(
        @NotNull @NotBlank String authToken
) {
}
