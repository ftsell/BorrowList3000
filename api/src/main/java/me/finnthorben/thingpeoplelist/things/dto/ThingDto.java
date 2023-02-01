package me.finnthorben.thingpeoplelist.things.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ThingDto {
    @NotNull
    UUID id;

    @NotNull
    String name;

    @Nullable
    String description;

    @NotNull
    String listName;

    @NotNull
    String personName;
}
