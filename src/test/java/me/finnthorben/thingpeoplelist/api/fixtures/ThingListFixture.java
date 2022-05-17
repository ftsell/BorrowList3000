package me.finnthorben.thingpeoplelist.api.fixtures;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ThingListFixture {

    private final ThingListService thingListService;

    public ThingList createEmptyThingList(User user) {
        return Objects.requireNonNull(
                thingListService.create("TestList", user).block()
        );
    }
}
