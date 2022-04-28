package me.finnthorben.thingpeoplelist.security.sessions;

import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Nullable
    Session findByToken(@NonNull String token);

    List<Session> findByUser(@NonNull User user);

    void deleteByUserAndIdIsNot(@NonNull User user, @NonNull UUID id);

    void deleteByUser(@NonNull User user);
}