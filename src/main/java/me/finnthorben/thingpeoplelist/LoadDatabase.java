package me.finnthorben.thingpeoplelist;

import me.finnthorben.thingpeoplelist.api.users.User;
import me.finnthorben.thingpeoplelist.api.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            log.info("Preloading: " + userRepository.save(new User("ftsell")));
            log.info("Preloading: " + userRepository.save(new User("obittner")));
        };
    }
}
