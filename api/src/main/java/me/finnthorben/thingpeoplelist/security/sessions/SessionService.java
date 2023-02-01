package me.finnthorben.thingpeoplelist.security.sessions;

import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SessionService {

    /**
     * Retrieve the session with the given ID from persistent storage and validate it so that only sessions linked
     * to active accounts are allowed.<br>
     * <p>
     * Also, automatically updates the last access time in the session object if desired
     */
    Mono<Session> getValidSession(String sessionId, boolean updateLastAccessTime);

    /**
     * Retrieve the session with the given ID from persistent storage and validate it so that only sessions linked
     * to active accounts are allowed.<br>
     * <p>
     * Also, automatically updates the last access time in the session object.
     */
    Mono<Session> getValidSession(String sessionId);

    /**
     * List all sessions of the given user
     */
    Flux<Session> listAllSessionsOfUser(User user);

    /**
     * Create a new session for the given user and attach the provided metadata
     */
    Mono<Session> createSession(
            @NonNull User user,
            @Nullable String ipAddress,
            @Nullable String userAgent
    );
}
