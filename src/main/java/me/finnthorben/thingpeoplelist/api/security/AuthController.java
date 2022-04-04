package me.finnthorben.thingpeoplelist.api.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginRequest;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginResponse;
import me.finnthorben.thingpeoplelist.api.security.dto.RegisterRequest;
import me.finnthorben.thingpeoplelist.api.security.dto.SessionDto;
import me.finnthorben.thingpeoplelist.api.users.IUserService;
import me.finnthorben.thingpeoplelist.api.users.User;
import org.springframework.http.MediaType;
import org.springframework.session.Session;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        HttpSession session = http.getSession(true);
        userService.login(session, request.username(), request.password(),
                new SessionInfo(http.getRemoteAddr(), http.getHeader("User-Agent")));
        return new LoginResponse(session.getId());
    }

    @PostMapping("/register")
    public void register(@RequestBody @Validated RegisterRequest request) {
        User user = userService.createUser(request.username(), request.password(), request.email());
    }

    @GetMapping("/sessions")
    @SecurityRequirement(name = "token")
    public List<SessionDto> listSessions(HttpSession session) {
        return userService.listAllSessionsOfUser(session)
                .stream()
                .map((iSession) -> {
                    SessionInfo sessionInfo = iSession.getAttribute(SessionInfo.SESSION_INFO_INDEX_NAME);

                    return new SessionDto(
                            sessionInfo.ipAddress(),
                            iSession.getCreationTime().atZone(ZoneId.of("UTC")),
                            iSession.getLastAccessedTime().atZone(ZoneId.of("UTC"))
                    );
                })
                .toList();
    }
}
