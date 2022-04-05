package me.finnthorben.thingpeoplelist.things;

import me.finnthorben.thingpeoplelist.lists.ThingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ThingRepository extends JpaRepository<Thing, UUID> {
    Optional<Thing> findByNameIgnoreCaseAndList(@NonNull String name, @NonNull ThingList list);

    Set<Thing> findByList(@NonNull ThingList list);
}
