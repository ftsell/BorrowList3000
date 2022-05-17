package me.finnthorben.thingpeoplelist.things;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {

    private final ThingRepository thingRepository;

    private final Scheduler jdbcScheduler;

    @Override
    public Mono<Thing> create(String name, String description, ThingList list, Person person) {
        return Mono.fromCallable(() -> thingRepository.save(new Thing(name, description, list, person)))
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<Thing> getAllForList(ThingList list) {
        return Mono.fromCallable(() -> thingRepository.findByList(list))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Thing> getByIdForUser(UUID id, User user) {
        return Mono.fromCallable(() -> thingRepository.findByIdAndList_User(id, user)
                        .orElseThrow(() -> new NoSuchThingException(id)))
                .subscribeOn(jdbcScheduler);
    }
}
