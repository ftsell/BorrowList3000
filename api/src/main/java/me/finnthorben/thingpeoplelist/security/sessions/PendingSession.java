package me.finnthorben.thingpeoplelist.security.sessions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.finnthorben.thingpeoplelist.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static me.finnthorben.thingpeoplelist.security.auth.SessionTokenAuthenticationConverter.SESSION_TOKEN_PREFIX;

/**
 * A PendingSession is used to store information about ongoing session sharing procedures
 */
@Entity
@Table(
        name = "PendingSession",
        uniqueConstraints = @UniqueConstraint(name = "uc_token", columnNames = {"token"})
)
@Getter
@NoArgsConstructor
@ToString
public class PendingSession {

    private static final Base64.Encoder tokenEncoder = Base64.getUrlEncoder();

    @Id
    @GeneratedValue
    private UUID id;

    @ToString.Exclude
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @NotNull
    @Setter
    ZonedDateTime expirationDate;

    public PendingSession(User user, ZonedDateTime expirationDate) {
        this.user = user;
        this.expirationDate = expirationDate;
        this.token = tokenEncoder.encodeToString(
                RandomStringUtils.random(32, 0, 0, true, true, null, new SecureRandom())
                        .getBytes(StandardCharsets.UTF_8));
    }

    public boolean isActive() {
        return getExpirationDate().isAfter(ZonedDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PendingSession session = (PendingSession) o;
        return id != null && Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
