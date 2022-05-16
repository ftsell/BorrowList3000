package me.finnthorben.thingpeoplelist.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class SessionTokenAuthenticationManager implements ReactiveAuthenticationManager {

    private final SessionService sessionService;

    @Override
    public Mono<Authentication> authenticate(Authentication auth) throws AuthenticationException {
        return filterAuthenticationClass(auth)
                .flatMap(filteredAuth -> validateToken((String) filteredAuth.getCredentials()));
    }

    private Mono<Authentication> filterAuthenticationClass(Authentication auth) {
        if (!(auth instanceof SessionTokenAuthentication)) {
            log.debug("Authentication " + auth.toString() + " will not be processed by SessionTokenAuthenticationManager");
            return Mono.empty();
        }

        log.debug("Authentication " + auth + " will be processed by SessionTokenAuthenticationManager");
        return Mono.just(auth);
    }

    private Mono<Authentication> validateToken(String token) {
        return sessionService.getValidSession(token)
                .switchIfEmpty(Mono.error(new InvalidSessionException()))
                .map(session -> {
                    SessionTokenAuthentication newAuth = new SessionTokenAuthentication(token, session, null);
                    newAuth.setAuthenticated(true);
                    return newAuth;
                });
    }
}
