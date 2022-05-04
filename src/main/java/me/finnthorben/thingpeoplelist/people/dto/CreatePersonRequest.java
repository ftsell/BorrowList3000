package me.finnthorben.thingpeoplelist.people.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePersonRequest {
    @NotBlank String name;
}