package me.finnthorben.thingpeoplelist.things;

import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ThingService {
    /**
     * Create a new persisted thing that is entered in the given list and associated with the given person
     */
    Mono<Thing> create(String name, String description, ThingList list, Person person);

    /**
     * Get all things that are entered in the given list
     */
    Flux<Thing> getAllForList(ThingList list);
}
