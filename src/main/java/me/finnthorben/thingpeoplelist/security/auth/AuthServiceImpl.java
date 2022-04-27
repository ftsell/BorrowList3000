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

    @Override
    public Mono<Session> login(String username, String password, String ipAddress, String userAgent) {
        return Mono.fromCallable(() -> {
            // authenticate the user
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = authManager.authenticate(authReq).block();    // TODO Make properly reactive
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);

            // persist the authenticated security context into the session
            Session session = new Session((User) auth.getPrincipal(), ipAddress, userAgent);
            sessionRepository.save(session);
            return session;
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public void logout(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    @Transactional
    public void logout(User user, Session exceptSession) {
        sessionRepository.deleteByUserAndIdIsNot(user, exceptSession.getId());
    }

    @Override
    @Transactional
    public void logout(User user) {
        sessionRepository.deleteByUser(user);
    }
}
