package me.finnthorben.thingpeoplelist.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    @NotBlank String sessionId;
    @NotBlank String ipAddress;
    @NotBlank String userAgent;
    @NotNull ZonedDateTime creationTime;
    @NotNull ZonedDateTime lastAccessTime;
    @NotNull boolean isCurrent;
}
