package me.finnthorben.thingpeoplelist.things;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.PeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.things.dto.CreateThingDto;
import me.finnthorben.thingpeoplelist.things.dto.ThingDto;
import me.finnthorben.thingpeoplelist.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/lists/{listName}/things", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class ThingController {

    private final ThingService thingService;
    private final ThingListService listService;
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    @Operation(summary = "List all things in this list")
    Flux<ThingDto> getAll(@PathVariable String listName, Authentication auth) {
        return listService
                .getByNameForUser(listName, (User) auth.getPrincipal())
                .flatMapMany(thingService::getAllForList)
                .map(thing -> modelMapper.map(thing, ThingDto.class));
    }

    @PostMapping("/")
    @Operation(summary = "Create a new thing in this list that is associated with the given person")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ThingDto> create(@PathVariable String listName,
                          @RequestBody @Validated CreateThingDto createRequest,
                          Authentication auth) {
        return Mono.zip(listService.getByNameForUser(listName, (User) auth.getPrincipal()),
                        peopleService.getByNameForUser(createRequest.getPersonName(), (User) auth.getPrincipal()))
                .flatMap(data -> thingService.create(createRequest.getName(), createRequest.getDescription(), data.getT1(), data.getT2()))
                .map(thing -> modelMapper.map(thing, ThingDto.class));
    }

}
