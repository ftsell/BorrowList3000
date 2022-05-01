package me.finnthorben.thingpeoplelist.users.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull
    String username;
    @NotNull
    String email;
}
