package me.finnthorben.thingpeoplelist.things;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {

    private final ThingRepository thingRepository;

    @Override
    public Thing create(String name, String description, ThingList list, Person person) {
        return thingRepository.save(new Thing(name, description, list, person));
    }

    @Override
    public Set<Thing> getAllForList(ThingList list) {
        return thingRepository.findByList(list);
    }
}
