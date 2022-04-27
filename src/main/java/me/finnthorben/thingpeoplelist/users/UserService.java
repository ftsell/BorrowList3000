package me.finnthorben.thingpeoplelist.users;

import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

public interface UserService extends ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    class UserAlreadyExistsException extends RuntimeException {}

    User createUser(String username, String password, String email);
}
