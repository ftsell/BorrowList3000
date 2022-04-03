package me.finnthorben.thingpeoplelist.api.security;

import me.finnthorben.thingpeoplelist.api.users.IUserService;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
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
        return Problem.create()
                .withTitle("User already exists")
                .withDetail("You tried to register a new user although a user with the given username already exists");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Problem userNotFoundHandler() {
        return Problem.create()
                .withTitle("Such a user does not exist")
                .withDetail("You tried to query for a user that does not exist or could not be found");
    }

    @ExceptionHandler(IUserService.InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    Problem invalidCredentialsHandler() {
        return Problem.create()
                .withTitle("Invalid Credentials")
                .withDetail("The provided credentials were invalid and you could not be logged in");
    }
}
