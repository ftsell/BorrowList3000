package me.finnthorben.thingpeoplelist.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.people.dto.CreatePersonRequest;
import me.finnthorben.thingpeoplelist.people.dto.PersonDto;
import me.finnthorben.thingpeoplelist.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/people", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "token")
@RequiredArgsConstructor
public class PeopleController {

    private final ModelMapper modelMapper;
    private final PeopleService peopleService;

    @GetMapping("/")
    @Operation(summary = "List all defined people")
    Flux<PersonDto> listAllPeople(Authentication auth) {
        return peopleService
                .getAllForUser((User) auth.getPrincipal())
                .map(person -> modelMapper.map(person, PersonDto.class));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new person")
    Mono<PersonDto> create(@RequestBody CreatePersonRequest personRequest, Authentication auth) {
        return peopleService
                .create(personRequest.getName(), (User) auth.getPrincipal())
                .map(person -> modelMapper.map(person, PersonDto.class));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve information about a specific person")
    Mono<PersonDto> getByName(@PathVariable UUID id, Authentication auth) {
        return peopleService
                .getByIdForUser(id, (User) auth.getPrincipal())
                .map(person -> modelMapper.map(person, PersonDto.class));
    }
}
