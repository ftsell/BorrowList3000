package me.finnthorben.thingpeoplelist.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class PendingSessionDto {
    @NonNull
    String token;
    @NonNull
    ZonedDateTime expirationDate;
}
