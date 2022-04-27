package me.finnthorben.thingpeoplelist.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User createUser(String username, String password, String email) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(new User(username, passwordEncoder.encode(password), email));
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        // TODO Make properly reactive with own scheduler
        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            ((User) user).setPassword(passwordEncoder.encode(newPassword));
            return Mono.just(userRepository.save((User) user));
        }
        return Mono.just(user);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // TODO Make properly reactive with own scheduler
        return Mono.just(userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
