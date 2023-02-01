package me.finnthorben.thingpeoplelist.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.security.sessions.*;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SessionRepository sessionRepository;

    private final PendingSessionRepository pendingSessionRepository;

    private final SessionService sessionService;

    private final Scheduler jdbcScheduler;

    private final TransactionTemplate transactionTemplate;

    @Override
    public Mono<Void> logoutSession(User user, UUID sessionId) throws CantLogoutLastSessionException {
        return Mono.fromRunnable(() -> transactionTemplate.execute(status -> {
                    if (sessionRepository.findByUser(user).size() == 1) {
                        throw new CantLogoutLastSessionException();
                    }

                    sessionRepository.deleteById(sessionId);
                    return null;
                }))
                .cast(Void.class)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> logoutExceptSession(User user, Session exceptSession) {
        return Mono.fromRunnable(() -> transactionTemplate.execute(status -> {
                    sessionRepository.deleteByUserAndIdIsNot(user, exceptSession.getId());
                    return null;
                }))
                .cast(Void.class)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Session> login(String pendingSessionToken, String ipAddress, String userAgent) throws InvalidPendingSessionException {
        return Mono
                .fromCallable(() -> pendingSessionRepository.findByToken(pendingSessionToken))
                .map(pendingSession -> pendingSession.orElseThrow(InvalidPendingSessionException::new))
                .flatMap(pendingSession -> {
                    if (!pendingSession.isActive()) {
                        return Mono.error(new AuthService.InvalidPendingSessionException());
                    }
                    return sessionService.createSession(pendingSession.getUser(), ipAddress, userAgent);
                })
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<PendingSession> createPendingSession(User user) {
        return Mono
                .fromCallable(() -> {
                    PendingSession session = new PendingSession(user, ZonedDateTime.now().plusMinutes(30));
                    return pendingSessionRepository.save(session);
                })
                .subscribeOn(jdbcScheduler);
    }
}
