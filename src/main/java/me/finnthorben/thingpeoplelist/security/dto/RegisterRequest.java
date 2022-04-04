package me.finnthorben.thingpeoplelist.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterRequest (
        @NotBlank @Size(min = 1) String username,
        @NotBlank @Size(min = 1) String password,
        @Email String email
){
}
