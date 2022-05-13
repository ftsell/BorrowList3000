package me.finnthorben.thingpeoplelist.people;

import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;


public interface PeopleService {

    class NoSuchPersonException extends NoSuchElementException {
        public NoSuchPersonException(UUID personId) {
            super("Person " + personId + " does not exist for current user");
        }
    }

    /**
     * Create a new persisted person with the specified data
     */
    Mono<Person> create(String name, User user);

    /**
     * Get all people that a specific user knows
     */
    Flux<Person> getAllForUser(User user);

    /**
     * Get a specific person by their ID but only if it is linked to the given user account
     */
    Mono<Person> getByIdForUser(UUID id, User user);
}
