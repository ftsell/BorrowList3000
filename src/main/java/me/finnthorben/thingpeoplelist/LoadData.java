package me.finnthorben.thingpeoplelist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.lists.IThingListService;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.IPeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.users.IUserService;
import me.finnthorben.thingpeoplelist.users.User;
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
    CommandLineRunner initDatabase(IUserService userService, IPeopleService peopleService, IThingListService listService) {
        return args -> {
            User user = userService.createUser("ftsell", "foobar123", null);
            log.info("Preloading: " + user.toString() + " (password=foobar123)");

            Person person = peopleService.create("Ole", user);
            log.info("Preloading: " + person.toString());

            ThingList list = listService.create("Borrowlist", user);
            log.info("Preloading: " + list.toString());
        };
    }
}
