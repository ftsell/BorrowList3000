package me.finnthorben.thingpeoplelist.api.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginRequest;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginResponse;
import me.finnthorben.thingpeoplelist.api.security.dto.RegisterRequest;
import me.finnthorben.thingpeoplelist.api.users.IUserService;
import me.finnthorben.thingpeoplelist.api.users.User;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(
        value = "/api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {

    private final IUserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request, HttpServletRequest http) {
        HttpSession session = userService.login(request.username(), request.password(),
                new SessionInfo(http.getRemoteAddr(), LocalDateTime.now()));
        return new LoginResponse(session.getId());
    }

    @PostMapping("/register")
    public void register(@RequestBody @Validated RegisterRequest request) {
        User user = userService.createUser(request.username(), request.password(), request.email());
    }

    @GetMapping("/sessions")
    @SecurityRequirement(name = "token")
    public List<SessionInfo> listSessions() {
        return new ArrayList<>();
    }
}
