package me.finnthorben.thingpeoplelist.things;

import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface ThingService {
    class NoSuchThingException extends NoSuchElementException {
        public NoSuchThingException(UUID thingId) {
            super("Thing " + thingId + " does not exist for current user");
        }
    }

    /**
     * Create a new persisted thing that is entered in the given list and associated with the given person
     */
    Mono<Thing> create(String name, String description, ThingList list, Person person);

    /**
     * Get all things that are entered in the given list
     */
    Flux<Thing> getAllForList(ThingList list);

    /**
     * Get a specific thing by their ID but only if it is linked to the given user account
     */
    Mono<Thing> getByIdForUser(UUID id, User user);
}
