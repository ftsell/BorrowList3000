package me.finnthorben.thingpeoplelist.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Scheduler jdbcScheduler;

    public Mono<User> createUser(String email) {
        return Mono.fromCallable(() -> userRepository.save(new User(
                        email != null ? email.toLowerCase() : null
                )))
                .subscribeOn(jdbcScheduler);
    }
}
