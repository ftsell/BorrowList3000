package me.finnthorben.thingpeoplelist.security.sessions;

import lombok.*;
import me.finnthorben.thingpeoplelist.users.User;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

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
    private UUID token;

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
        this.token = UUID.randomUUID();
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
