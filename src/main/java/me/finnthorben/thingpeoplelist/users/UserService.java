package me.finnthorben.thingpeoplelist.users;

import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> createUser(@Nullable String email);
}
