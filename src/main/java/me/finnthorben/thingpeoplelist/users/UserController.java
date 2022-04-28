package me.finnthorben.thingpeoplelist.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.users.dto.ChangeUserDto;
import me.finnthorben.thingpeoplelist.users.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "token")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Retrieve the currently logged in user")
    UserDto getMe(Authentication authentication) {
        return modelMapper.map(authentication.getPrincipal(), UserDto.class);
    }

    @PatchMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update information of the currently logged in user")
    Mono<UserDto> patchMe(@RequestBody @Validated ChangeUserDto changeRequest, Authentication authentication) {
        return userService.updatePassword((UserDetails) authentication.getPrincipal(), changeRequest.getPassword())
                .thenReturn(modelMapper.map(authentication.getPrincipal(), UserDto.class));
    }
}
