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
    List<ThingDto> getAll(@PathVariable String listName, Authentication auth) {
        ThingList list = listService.getByNameForUser(listName, (User) auth.getPrincipal()).block();
        return thingService.getAllForList(list)
                .stream()
                .map((thing) -> modelMapper.map(thing, ThingDto.class))
                .toList();
    }

    @PostMapping("/")
    @Operation(summary = "Create a new thing in this list that is associated with the given person")
    @ResponseStatus(HttpStatus.CREATED)
    ThingDto create(@PathVariable String listName,
                    @RequestBody @Validated CreateThingDto createRequest,
                    Authentication auth) {
        ThingList list = listService.getByNameForUser(listName, (User) auth.getPrincipal()).block();
        Person person = peopleService.getByNameForUser(createRequest.getPersonName(), (User) auth.getPrincipal()).block();
        return modelMapper.map(
                thingService.create(createRequest.getName(), createRequest.getDescription(), list, person),
                ThingDto.class
        );
    }

}
