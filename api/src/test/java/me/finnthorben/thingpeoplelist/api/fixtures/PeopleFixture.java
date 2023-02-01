package me.finnthorben.thingpeoplelist.api.fixtures;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.people.PeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeopleFixture {

    private final PeopleService peopleService;

    public Person createPerson(User user) {
        return Objects.requireNonNull(
                peopleService.create("TestPerson", user).block()
        );
    }
}
