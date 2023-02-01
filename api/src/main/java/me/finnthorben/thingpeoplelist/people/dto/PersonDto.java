package me.finnthorben.thingpeoplelist.people.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class PersonDto {
    @NotNull
    UUID id;
    @NotNull
    String name;
}
