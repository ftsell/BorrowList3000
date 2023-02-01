package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ThingListDto {
    @NotNull
    UUID id;
    @NotNull
    String name;
}
