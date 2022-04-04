package me.finnthorben.thingpeoplelist.security;

import javax.servlet.http.HttpSession;
import java.util.function.Supplier;

public interface IAuthService {
    /**
     * Perform authorization of a user account with the given credentials and associate it with a session if successful
     */
    HttpSession login(Supplier<HttpSession> sessionSupplier, SessionInfo info, String username, String password);

    /**
     * Delete the session with the given ID, effectively logging it out
     */
    void logout(String sessionId);
}
