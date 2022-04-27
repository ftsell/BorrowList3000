package me.finnthorben.thingpeoplelist.security.sessions;

import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface SessionService {

    /**
     * Retrieve the session with the given ID from persistent storage and validate it so that only sessions linked
     * to active accounts are allowed.<br>
     *
     * Also, automatically updates the last access time in the session object if desired
     */
    Mono<Session> getValidSession(UUID sessionId, boolean updateLastAccessTime);

    /**
     * Retrieve the session with the given ID from persistent storage and validate it so that only sessions linked
     * to active accounts are allowed.<br>
     *
     * Also, automatically updates the last access time in the session object.
     */
    Mono<Session> getValidSession(UUID sessionId);

    /**
     * List all sessions of the given user
     */
    Mono<List<Session>> listAllSessionsOfUser(User user);
}
