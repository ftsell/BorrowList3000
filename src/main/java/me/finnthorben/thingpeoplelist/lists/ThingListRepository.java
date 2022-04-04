package me.finnthorben.thingpeoplelist.lists;

import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ThingListRepository extends JpaRepository<ThingList, String> {
    Optional<ThingList> findByNameIgnoreCaseAndUser(@NonNull String name, @NonNull User user);

    Set<ThingList> findByUser(@NonNull User user);
}