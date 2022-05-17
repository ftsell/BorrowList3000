package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThingListRequest {
    @NotBlank
    @Size(min = 1)
    String name;
}
