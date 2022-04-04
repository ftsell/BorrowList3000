package me.finnthorben.thingpeoplelist.lists;

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

import java.util.List;

@RestController
@RequestMapping(value = "/api/lists", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class ThingListController {

    private final IThingListService listService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    List<ThingListDto> getAll(Authentication auth) {
        return listService.getAllForUser((User) auth.getPrincipal())
                .stream()
                .map((list) -> modelMapper.map(list, ThingListDto.class))
                .toList();
    }

    @GetMapping("/{name}")
    ThingListDto getByName(@PathVariable String name, Authentication auth) {
        return modelMapper.map(
                listService.getByNameForUser(name, (User) auth.getPrincipal()),
                ThingListDto.class
        );
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    ThingListDto create(@RequestBody ThingListDto listRequest, Authentication auth) {
        return modelMapper.map(
                listService.create(listRequest.getName(), (User) auth.getPrincipal()),
                ThingListDto.class
        );
    }
}
