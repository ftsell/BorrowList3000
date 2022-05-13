package me.finnthorben.thingpeoplelist.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPerformRequest {
    @NonNull
    String token;
}
