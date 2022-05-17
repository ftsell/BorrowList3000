package me.finnthorben.thingpeoplelist.lists;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.lists.dto.CreateThingListRequest;
import me.finnthorben.thingpeoplelist.lists.dto.PatchThingListRequest;
import me.finnthorben.thingpeoplelist.lists.dto.ThingListDto;
import me.finnthorben.thingpeoplelist.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve information about a specific list")
    Mono<ThingListDto> getById(@PathVariable UUID id, Authentication auth) {
        return listService
                .getByIdForUser(id, (User) auth.getPrincipal())
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update the specified list with the given data")
    Mono<ThingListDto> updateById(@PathVariable UUID id,
                                    @RequestBody @Validated PatchThingListRequest patchRequest,
                                    Authentication auth) {
        return listService
                .getByIdForUser(id, (User) auth.getPrincipal())
                .flatMap(list -> {
                    modelMapper.map(patchRequest, list);
                    return listService.saveThingList(list);
                })
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new list")
    Mono<ThingListDto> create(@RequestBody CreateThingListRequest request, Authentication auth) {
        return listService
                .create(request.getName(), (User) auth.getPrincipal())
                .map(list -> modelMapper.map(list, ThingListDto.class));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the specified list")
    Mono<Void> delete(@PathVariable UUID id, Authentication auth) {
        return listService.delete(id);
    }
}
