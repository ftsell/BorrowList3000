package me.finnthorben.thingpeoplelist.people;

import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<Person, UUID> {
    boolean existsByNameIgnoreCaseAndUser(@NonNull String name, @NonNull User user);

    Optional<Person> findByNameIgnoreCaseAndUser(@NonNull String name, @NonNull User user);

    Optional<Person> findByIdAndUser(@NonNull UUID id, @NonNull User user);

    Set<Person> findByUser(@NotNull User user);
}
