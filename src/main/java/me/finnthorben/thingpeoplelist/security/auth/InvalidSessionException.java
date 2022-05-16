package me.finnthorben.thingpeoplelist.security.auth;

import org.springframework.security.core.AuthenticationException;

public class InvalidSessionException extends AuthenticationException {
    public InvalidSessionException() {
        super("Invalid Session Token");
    }
}
