package me.finnthorben.thingpeoplelist.api.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(name = "uc_username", columnNames = {"username"})
})
@Getter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @NotBlank
    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
