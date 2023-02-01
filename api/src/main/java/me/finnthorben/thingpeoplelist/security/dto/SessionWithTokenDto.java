package me.finnthorben.thingpeoplelist.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SessionWithTokenDto extends SessionDto {
    @NonNull
    String token;
}
