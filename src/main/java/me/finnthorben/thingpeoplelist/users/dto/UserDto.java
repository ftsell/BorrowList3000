package me.finnthorben.thingpeoplelist.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {
    @NonNull
    UUID id;

    @Nullable
    String email;
}
