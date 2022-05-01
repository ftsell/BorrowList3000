package me.finnthorben.thingpeoplelist.people.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class PersonDto {
    UUID id;
    String name;
}
