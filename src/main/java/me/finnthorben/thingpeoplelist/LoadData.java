package me.finnthorben.thingpeoplelist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.api.users.IUserService;
import me.finnthorben.thingpeoplelist.api.users.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("dev")
public class LoadData {
    @Bean
    CommandLineRunner initDatabase(IUserService userService) {
        return args -> {
            userService.createUser("ftsell", "foobar123", null);
            log.info("Preloading: username=ftsell password=foobar123");
        };
    }
}
