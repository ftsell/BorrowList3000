package me.finnthorben.thingpeoplelist.api.users;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/users")
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
        return userRepository.findUserByUsername(username).orElseThrow();
    }
}
