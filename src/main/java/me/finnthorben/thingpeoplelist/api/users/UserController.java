package me.finnthorben.thingpeoplelist.api.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "token")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping(value = "/", produces = "application/json")
    @Profile("dev")
    List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{username}", produces = "application/json")
    User getUser(@PathVariable @NotNull @NotBlank String username) {
        return userRepository.findUserByUsernameIgnoreCase(username).orElseThrow();
    }
}
