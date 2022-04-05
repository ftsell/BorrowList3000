package me.finnthorben.thingpeoplelist.things.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateThingDto {
    @NotBlank
    @Size(min = 1)
    String name;

    @Nullable
    String description;

    @NotBlank
    String personName;
}
