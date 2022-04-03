package me.finnthorben.thingpeoplelist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.api.users.User;
import me.finnthorben.thingpeoplelist.api.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("dev")
public class LoadDatabase {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            log.info("Preloading: " + userRepository.save(new User("ftsell", passwordEncoder.encode("foobar123"), null)));
            log.info("Preloading: " + userRepository.save(new User("obittner", passwordEncoder.encode("foobar123"), null)));
        };
    }
}
