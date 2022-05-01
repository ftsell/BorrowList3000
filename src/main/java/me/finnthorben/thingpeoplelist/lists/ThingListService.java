package me.finnthorben.thingpeoplelist.lists;

import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface ThingListService {
    class ThingListAlreadyExistsException extends RuntimeException {
        public ThingListAlreadyExistsException(String thingListName, User user) {
            super("User " + user.getUsername() + " already has a '" + thingListName + "' list");
        }
    }

    class NoSuchThingListException extends NoSuchElementException {
        public NoSuchThingListException(UUID id, User user) {
            super("User " + user.getUsername() + " does not have a list " + id);
        }
    }

    /**
     * Create a new list of things owned by the given user
     */
    Mono<ThingList> create(String name, User user);

    /**
     * Get all ThingLists of the given user
     */
    Flux<ThingList> getAllForUser(User user);

    /**
     * Get a specific ThingList by its id but only if it is owned by the given user
     */
    Mono<ThingList> getByIdForUser(UUID id, User user);

    /**
     * Persist any changes made to the given list
     */
    Mono<ThingList> saveThingList(ThingList list);
}
