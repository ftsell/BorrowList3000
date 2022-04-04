package me.finnthorben.thingpeoplelist.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsernameIgnoreCase(@NotBlank String username);

    boolean existsByUsernameIgnoreCase(@NotNull String username);
}
