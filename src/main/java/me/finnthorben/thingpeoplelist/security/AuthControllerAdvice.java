package me.finnthorben.thingpeoplelist.security;

import me.finnthorben.thingpeoplelist.genericapi.Problem;
import me.finnthorben.thingpeoplelist.users.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(IUserService.UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    Problem userAlreadyExistsHandler() {
        return new Problem(
                "User already exists",
                "You tried to register a new user although a user with the given username already exists"
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Problem userNotFoundHandler() {
        return new Problem(
                "Such a user does not exist",
                "You tried to query for a user that does not exist or could not be found"
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    Problem invalidCredentialsHandler() {
        return new Problem(
                "Invalid Credentials",
                "The provided credentials were invalid and you could not not be logged in"
        );
    }
}
