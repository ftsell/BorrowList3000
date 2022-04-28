package me.finnthorben.thingpeoplelist.security.sessions;

import lombok.*;
import me.finnthorben.thingpeoplelist.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static me.finnthorben.thingpeoplelist.security.auth.SessionTokenAuthenticationConverter.SESSION_TOKEN_PREFIX;

@Entity
@Table(
        name = "Session",
        uniqueConstraints = @UniqueConstraint(name = "unique_tokens", columnNames = {"token"})
)
@Getter
@NoArgsConstructor
@ToString
public class Session {
    @Id
    @GeneratedValue
    private UUID id;

    @ToString.Exclude
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @NotBlank
    String ipAddress;

    @NotBlank
    String userAgent;

    @NotNull
    ZonedDateTime creationTime;

    @NotNull
    @Setter
    ZonedDateTime lastAccessTime;

    public Session(User user, String ipAddress, String userAgent) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.token = SESSION_TOKEN_PREFIX + " " + RandomStringUtils.random(32, 0, 0, true, true, null, new SecureRandom());
        this.creationTime = ZonedDateTime.now();
        this.lastAccessTime = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Session session = (Session) o;
        return id != null && Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
