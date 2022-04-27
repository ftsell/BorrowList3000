package me.finnthorben.thingpeoplelist.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    @Override
    public Person create(String name, User user) {
        if (peopleRepository.existsByNameIgnoreCaseAndUser(name, user)) {
            throw new PersonAlreadyExistsException(name, user);
        }
        Person x = new Person(name, user);
        return peopleRepository.save(x);
    }

    @Override
    public Set<Person> getAllForUser(User user) {
        return peopleRepository.findByUser(user);
    }

    @Override
    public Person getByNameForUser(String name, User user) {
        return peopleRepository.findByNameIgnoreCaseAndUser(name, user)
                .orElseThrow(() -> new NoSuchPersonException(name, user));
    }
}
