package me.finnthorben.thingpeoplelist.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.security.sessions.SessionRepository;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ReactiveAuthenticationManager authManager;
    private final SessionRepository sessionRepository;

    private final Scheduler jdbcScheduler;

    private final TransactionTemplate transactionTemplate;

    @Override
    public Mono<Session> login(String username, String password, String ipAddress, String userAgent) {
        return Mono
                .just(new UsernamePasswordAuthenticationToken(username, password))
                .flatMap(authManager::authenticate)
                .doOnNext(auth -> {
                    // set authentication for the current thread
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(auth);
                })
                .map(auth -> {
                    // construct a session from the validated authentication
                    Session session = new Session((User) auth.getPrincipal(), ipAddress, userAgent);
                    return sessionRepository.save(session);
                })
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> logout(UUID sessionId) {
        return Mono.fromRunnable(() -> transactionTemplate.execute(status -> {
                    sessionRepository.deleteById(sessionId);
                    return null;
                }))
                .cast(Void.class)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> logout(User user, Session exceptSession) {
        return Mono.fromRunnable(() -> transactionTemplate.execute(status -> {
                    sessionRepository.deleteByUserAndIdIsNot(user, exceptSession.getId());
                    return null;
                }))
                .cast(Void.class)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> logout(User user) {
        return Mono.fromRunnable(() -> transactionTemplate.execute(status -> {
                    sessionRepository.deleteByUser(user);
                    return null;
                }))
                .cast(Void.class)
                .subscribeOn(jdbcScheduler);
    }
}
