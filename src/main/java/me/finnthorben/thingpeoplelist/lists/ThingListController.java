package me.finnthorben.thingpeoplelist.lists;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.lists.dto.ThingListDto;
import me.finnthorben.thingpeoplelist.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/lists", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class ThingListController {

    private final ThingListService listService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    @Operation(summary = "List all lists")
    Flux<ThingListDto> getAll(Authentication auth) {
        return listService
                .getAllForUser((User) auth.getPrincipal())
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }

    @GetMapping("/{name}")
    @Operation(summary = "Retrieve information about a specific list")
    Mono<ThingListDto> getByName(@PathVariable String name, Authentication auth) {
        return listService
                .getByNameForUser(name, (User) auth.getPrincipal())
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new list")
    Mono<ThingListDto> create(@RequestBody ThingListDto listRequest, Authentication auth) {
        return listService
                .create(listRequest.getName(), (User) auth.getPrincipal())
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }
}
