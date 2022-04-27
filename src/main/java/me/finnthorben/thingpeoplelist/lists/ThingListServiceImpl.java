package me.finnthorben.thingpeoplelist.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThingListServiceImpl implements ThingListService {

    private final ThingListRepository thingListRepository;

    @Override
    public ThingList create(String name, User user) {
        if (thingListRepository.findByNameIgnoreCaseAndUser(name, user).isPresent()) {
            throw new ThingListAlreadyExistsException(name, user);
        }

        return thingListRepository.save(new ThingList(name, user));
    }

    @Override
    public Set<ThingList> getAllForUser(User user) {
        return thingListRepository.findByUser(user);
    }

    @Override
    public ThingList getByNameForUser(String name, User user) {
        return thingListRepository.findByNameIgnoreCaseAndUser(name, user)
                .orElseThrow(() -> new NoSuchThingListException(name, user));
    }
}
