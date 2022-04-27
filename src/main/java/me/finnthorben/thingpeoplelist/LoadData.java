package me.finnthorben.thingpeoplelist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.PeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.things.ThingService;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.users.UserService;
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
    CommandLineRunner initDatabase(UserService userService, PeopleService peopleService, ThingListService listService, ThingService thingService) {
        return args -> {
            User user = userService.createUser("ftsell", "foobar123", null);
            log.info("Preloading: " + user.toString() + " (password=foobar123)");

            Person person = peopleService.create("Ole", user).block();
            log.info("Preloading: " + person.toString());

            ThingList list = listService.create("Borrowlist", user).block();
            log.info("Preloading: " + list.toString());

            Thing thing = thingService.create("Book", "Lord of the Rings 1", list, person);
            log.info("Preloading: " + thing.toString());
        };
    }
}
