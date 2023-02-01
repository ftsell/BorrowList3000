package me.finnthorben.thingpeoplelist.api.fixtures;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.people.PeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.things.ThingService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ThingListFixture {

    private final ThingListService thingListService;

    private final ThingService thingService;

    private final PeopleService peopleService;

    public ThingList createEmptyThingList(User user) {
        return Objects.requireNonNull(
                thingListService.create("TestList", user).block()
        );
    }

    public Pair<ThingList, Thing> createThingListWithThing(User user) {
        ThingList list = Objects.requireNonNull(thingListService.create("TestList", user).block());
        Person person = Objects.requireNonNull(peopleService.create("TestPerson", user).block());
        Thing thing = Objects.requireNonNull(thingService.create("TestThing", null, list, person).block());
        return Pair.of(list, thing);
    }
}
