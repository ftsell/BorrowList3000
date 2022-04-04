package me.finnthorben.thingpeoplelist.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public record SessionDto(
        @NotBlank String sessionId,
        @NotBlank String ipAddress,
        @NotNull ZonedDateTime creationTime,
        @NotNull ZonedDateTime lastAccessTime,
        boolean isCurrent
        ) {
}
