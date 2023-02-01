package me.finnthorben.thingpeoplelist.security;

import me.finnthorben.thingpeoplelist.genericapi.Problem;
import me.finnthorben.thingpeoplelist.security.auth.AuthService;
import me.finnthorben.thingpeoplelist.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(AuthService.CantLogoutLastSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Problem cantLogoutLastSessionHandler(AuthService.CantLogoutLastSessionException exception) {
        return new Problem(
                "Cannot logout the last session",
                exception.getMessage()
        );
    }

    @ExceptionHandler(AuthService.InvalidPendingSessionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    Problem invalidPendingSessionHandler(AuthService.InvalidPendingSessionException exception) {
        return new Problem(
                "Cannot perform login",
                exception.getMessage()
        );
    }
}
