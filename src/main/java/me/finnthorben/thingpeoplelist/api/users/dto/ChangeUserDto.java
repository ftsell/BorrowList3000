package me.finnthorben.thingpeoplelist.api.users.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Setter(AccessLevel.NONE)
public class ChangeUserDto {
    @NotBlank @Size(min = 1) String password;
}
