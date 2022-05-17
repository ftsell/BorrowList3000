package me.finnthorben.thingpeoplelist.things.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThingRequest {
    @NotBlank
    @Size(min = 1)
    String name;

    @Nullable
    String description;

    @NotBlank
    UUID personId;
}
