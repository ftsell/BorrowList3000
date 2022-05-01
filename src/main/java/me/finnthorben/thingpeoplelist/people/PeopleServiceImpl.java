package me.finnthorben.thingpeoplelist.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    private final Scheduler jdbcScheduler;

    @Override
    public Mono<Person> create(String name, User user) {
        return Mono.fromCallable(() -> {
            if (peopleRepository.existsByNameIgnoreCaseAndUser(name, user))
                throw new PersonAlreadyExistsException(name, user);

            return peopleRepository.save(new Person(name, user));
        }).subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<Person> getAllForUser(User user) {
        return Mono.fromCallable(() -> peopleRepository.findByUser(user))
                .subscribeOn(jdbcScheduler)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Person> getByIdForUser(UUID id, User user) {
        return Mono.fromCallable(() -> peopleRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoSuchPersonException(id, user)))
                .subscribeOn(jdbcScheduler);
    }
}
