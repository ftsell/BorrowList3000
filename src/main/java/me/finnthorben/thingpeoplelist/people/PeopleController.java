package me.finnthorben.thingpeoplelist.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.people.dto.PersonDto;
import me.finnthorben.thingpeoplelist.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/people", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "token")
@RequiredArgsConstructor
public class PeopleController {

    private final ModelMapper modelMapper;
    private final PeopleService peopleService;

    @GetMapping("/")
    @Operation(summary = "List all defined people")
    List<PersonDto> listAllPeople(Authentication auth) {
        return peopleService.getAllForUser((User) auth.getPrincipal())
                .stream()
                .map((person) -> modelMapper.map(person, PersonDto.class))
                .toList();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new person")
    PersonDto create(@RequestBody PersonDto personRequest, Authentication auth) {
        return modelMapper.map(
                peopleService.create(personRequest.getName(), (User) auth.getPrincipal()),
                PersonDto.class
        );
    }

    @GetMapping("/{name}")
    @Operation(summary = "Retrieve information about a specific person")
    PersonDto getByName(@PathVariable String name, Authentication auth) {
        return modelMapper.map(
                peopleService.getByNameForUser(name, (User) auth.getPrincipal()),
                PersonDto.class);
    }
}
