package me.finnthorben.thingpeoplelist.security.sessions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingSessionRepository extends JpaRepository<PendingSession, UUID> {
    Optional<PendingSession> findByToken(@NonNull String token);
}
