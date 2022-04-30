package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class ThingListDto {
    UUID id;
    String name;
}
