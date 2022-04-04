package me.finnthorben.thingpeoplelist.api.users;

import me.finnthorben.thingpeoplelist.api.security.SessionInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.Session;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService extends UserDetailsService {
    class UserAlreadyExistsException extends RuntimeException {}

    class InvalidCredentialsException extends RuntimeException {}

    User createUser(String username, String password, String email);

    void login(HttpSession session, String username, String password, SessionInfo info);

    /**
     * List all sessions associated with the user that is associated with the given session.
     */
    List<? extends Session> listAllSessionsOfUser(HttpSession session1);
}
