package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ThingListDto {
    @NotBlank @Size(min = 1) String name;
}
