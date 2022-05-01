package me.finnthorben.thingpeoplelist.lists;

import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ThingListRepository extends JpaRepository<ThingList, UUID> {
    Optional<ThingList> findByIdAndUser(@NonNull UUID id, @NonNull User user);

    Set<ThingList> findByUser(@NonNull User user);

    boolean existsByNameIgnoreCaseAndUser(@NonNull String name, @NonNull User user);
}