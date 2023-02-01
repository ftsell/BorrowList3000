package me.finnthorben.thingpeoplelist.api.fixtures;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserFixture {
    private final UserService userService;

    private final SessionService sessionService;

    public Pair<User, Session> createUserWithSession() {
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session = Objects.requireNonNull(sessionService.createSession(user, "127.0.0.1", "test").block());
        return Pair.of(user, session);
    }
}
