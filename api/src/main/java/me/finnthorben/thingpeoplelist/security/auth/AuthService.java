package me.finnthorben.thingpeoplelist.security.auth;

import me.finnthorben.thingpeoplelist.security.sessions.PendingSession;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthService {
    class CantLogoutLastSessionException extends RuntimeException {
        public CantLogoutLastSessionException() {
            super("Cannot logout the only remaining session because that would leave the user account with no way to log back in");
        }
    }

    class InvalidPendingSessionException extends RuntimeException {
        public InvalidPendingSessionException() {
            super("The provided token is invalid. It does not identify a valid pending session.");
        }
    }

    /**
     * Perform the login using the given pendingSessionToken and return the actual new session
     */
    Mono<Session> login(String pendingSessionToken, String ipAddress, String userAgent) throws InvalidPendingSessionException;

    /**
     * Prepare logging in to a new device by creating a new PendingSession.
     * The token of the PendingSession must be used to activate the session on the new device.
     */
    Mono<PendingSession> createPendingSession(User user);

    /**
     * Delete the session with the given ID, effectively logging it out
     */
    Mono<Void> logoutSession(User user, UUID sessionId) throws CantLogoutLastSessionException;

    /**
     * Logout all sessions of the given user except the given session
     */
    Mono<Void> logoutExceptSession(User user, Session exceptSession);
}
