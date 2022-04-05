package me.finnthorben.thingpeoplelist.things;

import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;

import java.util.NoSuchElementException;
import java.util.Set;

public interface IThingService {
    class NoSuchThingException extends NoSuchElementException {
        public NoSuchThingException(String thingName, ThingList list) {
            super("There is no thing " + thingName + " in list " + list.getName());
        }
    }

    /**
     * Create a new persisted thing that is entered in the given list and associated with the given person
     */
    Thing create(String name, String description, ThingList list, Person person);

    /**
     * Get all things that are entered in the given list
     */
    Set<Thing> getAllForList(ThingList list);
}
