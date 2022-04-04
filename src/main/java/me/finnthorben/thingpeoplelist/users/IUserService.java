package me.finnthorben.thingpeoplelist.users;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.Session;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService extends UserDetailsService, UserDetailsPasswordService {
    class UserAlreadyExistsException extends RuntimeException {}

    class InvalidCredentialsException extends RuntimeException {}

    User createUser(String username, String password, String email);

    /**
     * List all sessions associated with the user that is associated with the given session.
     */
    List<? extends Session> listAllSessionsOfUser(HttpSession session1);
}
