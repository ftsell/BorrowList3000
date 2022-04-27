package me.finnthorben.thingpeoplelist.people;

import me.finnthorben.thingpeoplelist.users.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;


public interface PeopleService {
    class PersonAlreadyExistsException extends RuntimeException {
        public PersonAlreadyExistsException(String personName, User user) {
            super("Person with name " + personName + " already exists for user " + user.getUsername());
        }
    }

    class NoSuchPersonException extends NoSuchElementException {
        public NoSuchPersonException(String personName, User user) {
            super("Person with name " + personName + " does not exist for user " + user.getUsername());
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
     * Get a specific person by name that a given user knows
     */
    Mono<Person> getByNameForUser(String name, User user);
}
