package me.finnthorben.thingpeoplelist.security.sessions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Mono<Session> getValidSession(UUID sessionId, boolean updateLastAccessTime) {
        // TODO Make properly reactive with own scheduler
        // TODO Validate that the user account is valid
        return Mono.justOrEmpty(sessionRepository.findByToken(sessionId))
                .doOnNext(session -> {
                    session.setLastAccessTime(ZonedDateTime.now());
                    sessionRepository.save(session);
                });
    }

    @Override
    public Mono<Session> getValidSession(@NonNull UUID sessionId) {
        return getValidSession(sessionId, true);
    }

    @Override
    public Mono<List<Session>> listAllSessionsOfUser(User user) {
        // TODO Make properly reactive with own scheduler
        return Mono.just(sessionRepository.findByUser(user));
    }
}
