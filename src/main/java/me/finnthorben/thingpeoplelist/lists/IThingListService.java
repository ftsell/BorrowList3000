package me.finnthorben.thingpeoplelist.lists;

import me.finnthorben.thingpeoplelist.users.User;

import java.util.NoSuchElementException;
import java.util.Set;

public interface IThingListService {
    class ThingListAlreadyExistsException extends RuntimeException {
        public ThingListAlreadyExistsException(String thingListName, User user) {
            super("User " + user.getUsername() + " already has a '" + thingListName + "' list");
        }
    }

    class NoSuchThingListException extends NoSuchElementException {
        public NoSuchThingListException(String thingListName, User user) {
            super("User " + user.getUsername() + " does not have a list named " + thingListName);
        }
    }

    /**
     * Create a new list of things owned by the given user
     */
    ThingList create(String name, User user);

    /**
     * Get all ThingLists of the given user
     */
    Set<ThingList> getAllForUser(User user);

    /**
     * Get a specific ThingList by name that the given user owns
     */
    ThingList getByNameForUser(String name, User user);
}