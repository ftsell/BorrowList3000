package me.finnthorben.thingpeoplelist.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.security.auth.AuthService;
import me.finnthorben.thingpeoplelist.security.auth.SessionTokenAuthentication;
import me.finnthorben.thingpeoplelist.security.dto.LoginPerformRequest;
import me.finnthorben.thingpeoplelist.security.dto.PendingSessionDto;
import me.finnthorben.thingpeoplelist.security.dto.RegisterRequest;
import me.finnthorben.thingpeoplelist.security.dto.SessionDto;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(
        value = "/api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    private final SessionService sessionService;

    private final ModelMapper modelMapper;

    @PostMapping("/register")
    @Operation(summary = "Register a new user account")
    public Mono<SessionDto> register(@RequestBody @Validated RegisterRequest request) {
        return userService.createUser(request.getEmail())
                .flatMap(user -> sessionService.createSession(user, null, null))
                .map(session -> modelMapper.map(session, SessionDto.class));
    }

    @PostMapping("/login/prepare")
    @Operation(summary = "Prepare login to a new device")
    public Mono<PendingSessionDto> prepareLogin(Authentication auth) {
        return authService
                .createPendingSession((User) auth.getPrincipal())
                .map(session -> modelMapper.map(session, PendingSessionDto.class));
    }

    @PostMapping("/login/perform")
    @Operation(summary = "Perform the login that was previously prepared")
    public Mono<SessionDto> performLogin(@RequestBody @Validated LoginPerformRequest request, ServerWebExchange http) {
        return authService
                .login(
                        request.getToken(),
                        // TODO Improve this mess of a chaining
                        Optional.ofNullable(http.getRequest().getRemoteAddress()).map(InetSocketAddress::toString).orElse(""),
                        Optional.ofNullable(http.getRequest().getHeaders().get(HttpHeaders.USER_AGENT)).flatMap(l -> l.stream().findFirst()).orElse("")
                )
                .map(session -> modelMapper.map(session, SessionDto.class));
    }

    @GetMapping("/sessions")
    @SecurityRequirement(name = "token")
    @Operation(summary = "List all active sessions")
    public Flux<SessionDto> listSessions(Authentication auth) {
        return sessionService
                .listAllSessionsOfUser((User) auth.getPrincipal())
                .map(session -> {
                    SessionDto dto = modelMapper.map(session, SessionDto.class);
                    dto.setCurrent(auth.getCredentials().equals(session.getToken()));
                    return dto;
                });
    }

    @DeleteMapping("/sessions")
    @SecurityRequirement(name = "token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logout from all active sessions")
    public Mono<Void> logoutAllSession(Authentication auth) {
        SessionTokenAuthentication sessionAuth = (SessionTokenAuthentication) auth;
        return authService.logoutExceptSession(sessionAuth.getUser(), sessionAuth.getSession());
    }

    @DeleteMapping("/sessions/{sessionId}")
    @SecurityRequirement(name = "token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logout the given session")
    public Mono<Void> logoutSession(@PathVariable @NotBlank UUID sessionId, Authentication auth) {
        return authService.logoutSession((User) auth.getPrincipal(), sessionId);
    }
}
