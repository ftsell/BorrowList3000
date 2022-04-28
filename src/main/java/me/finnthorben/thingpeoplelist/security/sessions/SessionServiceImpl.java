package me.finnthorben.thingpeoplelist.security.sessions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final Scheduler jdbcScheduler;

    @Override
    public Mono<Session> getValidSession(String sessionId, boolean updateLastAccessTime) {
        // TODO Validate that the user account is valid
        return Mono.fromCallable(() -> sessionRepository.findByToken(sessionId))
                .doOnNext(session -> {
                    session.setLastAccessTime(ZonedDateTime.now());
                    sessionRepository.save(session);
                })
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Session> getValidSession(@NonNull String sessionId) {
        return getValidSession(sessionId, true);
    }

    @Override
    public Flux<Session> listAllSessionsOfUser(User user) {
        return Mono.fromCallable(() -> sessionRepository.findByUser(user))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }
}
