package me.finnthorben.thingpeoplelist.api.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginRequest;
import me.finnthorben.thingpeoplelist.api.security.dto.LoginResponse;
import me.finnthorben.thingpeoplelist.api.security.dto.RegisterRequest;
import me.finnthorben.thingpeoplelist.api.security.dto.SessionDto;
import me.finnthorben.thingpeoplelist.api.users.IUserService;
import me.finnthorben.thingpeoplelist.api.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.session.Session;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.ZoneId;
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
    private final IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user account")
    public void register(@RequestBody @Validated RegisterRequest request) {
        User user = userService.createUser(request.username(), request.password(), request.email());
    }

    @PostMapping("/login")
    @Operation(summary = "Login to an existing user account")
    public LoginResponse login(@RequestBody @Validated LoginRequest request, HttpServletRequest http) {
        HttpSession session = authService.login(() -> http.getSession(true), new SessionInfo(http.getRemoteAddr(), http.getHeader("User-Agent")),
                request.username(), request.password());
        return new LoginResponse(session.getId());
    }

    @GetMapping("/sessions")
    @SecurityRequirement(name = "token")
    @Operation(summary = "List all active sessions")
    public List<SessionDto> listSessions(HttpSession session) {
        return userService.listAllSessionsOfUser(session)
                .stream()
                .map((iSession) -> {
                    SessionInfo sessionInfo = iSession.getAttribute(SessionInfo.SESSION_INFO_INDEX_NAME);

                    return new SessionDto(
                            iSession.getId(),
                            sessionInfo.ipAddress(),
                            iSession.getCreationTime().atZone(ZoneId.of("UTC")),
                            iSession.getLastAccessedTime().atZone(ZoneId.of("UTC")),
                            iSession.getId().equals(session.getId())
                    );
                })
                .toList();
    }

    @DeleteMapping("/sessions")
    @SecurityRequirement(name = "token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logout from all active sessions")
    public void logoutAllSession(@RequestParam(defaultValue = "false", required = false) boolean includingCurrent, HttpSession session) {
        List<? extends Session> sessions = userService.listAllSessionsOfUser(session);

        for (Session iSession : sessions) {
            if (!includingCurrent && iSession.getId().equals(session.getId())) {
                continue;
            }
            authService.logout(iSession.getId());
        }
    }

    @DeleteMapping("/sessions/{sessionId}")
    @SecurityRequirement(name = "token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logout the given session")
    public void logoutSession(@PathVariable @NotBlank String sessionId) {
        authService.logout(sessionId);
    }
}
