package me.finnthorben.thingpeoplelist.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final Scheduler jdbcScheduler;

    @Override
    public Mono<User> createUser(String username, String password, String email) {
        return Mono.fromCallable(() -> {
            if (userRepository.existsByUsernameIgnoreCase(username))
                throw new UserAlreadyExistsException();

            return userRepository.save(new User(username, passwordEncoder.encode(password), email));
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return Mono.fromCallable(() -> {
            if (!passwordEncoder.matches(newPassword, user.getPassword())) {
                ((User) user).setPassword(passwordEncoder.encode(newPassword));
                return userRepository.save((User) user);
            }
            return user;
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)))
                .cast(UserDetails.class)
                .subscribeOn(jdbcScheduler);
    }
}
