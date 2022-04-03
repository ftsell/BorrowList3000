package me.finnthorben.thingpeoplelist.api.users;

import me.finnthorben.thingpeoplelist.api.security.SessionInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;

public interface IUserService extends UserDetailsService {
    class UserAlreadyExistsException extends RuntimeException {}

    class InvalidCredentialsException extends RuntimeException {}

    User createUser(String username, String password, String email);

    HttpSession login(String username, String password, SessionInfo info);
}
