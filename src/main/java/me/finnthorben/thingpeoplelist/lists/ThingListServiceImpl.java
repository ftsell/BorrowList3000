package me.finnthorben.thingpeoplelist.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThingListServiceImpl implements ThingListService {

    private final ThingListRepository thingListRepository;

    private final Scheduler jdbcScheduler;

    @Override
    public Mono<ThingList> create(String name, User user) {
        return Mono.fromCallable(() -> {
            if (thingListRepository.findByNameIgnoreCaseAndUser(name, user).isPresent()) {
                throw new ThingListAlreadyExistsException(name, user);
            }

            return thingListRepository.save(new ThingList(name, user));
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<ThingList> getAllForUser(User user) {
        return Mono.fromCallable(() -> thingListRepository.findByUser(user))
                .subscribeOn(jdbcScheduler)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<ThingList> getByNameForUser(String name, User user) {
        return Mono.fromCallable(() -> thingListRepository.findByNameIgnoreCaseAndUser(name, user)
                .orElseThrow(() -> new NoSuchThingListException(name, user)))
                .subscribeOn(jdbcScheduler);
    }
}
