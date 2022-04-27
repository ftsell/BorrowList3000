package me.finnthorben.thingpeoplelist.security.auth;

import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IAuthService {
    /**
     * Perform authorization of a user account with the given credentials and associate it with a session if successful
     */
    Mono<Session> login(String username, String password, String ipAddress, String userAgent);

    /**
     * Delete the session with the given ID, effectively logging it out
     */
    void logout(UUID sessionId);

    /**
     * Logout all sessions of the given user except the given session
     */
    void logout(User user, Session exceptSession);

    /**
     * Logout all sessions of the given user
     */
    void logout(User user);
}
