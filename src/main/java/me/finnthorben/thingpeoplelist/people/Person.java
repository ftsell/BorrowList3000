package me.finnthorben.thingpeoplelist.people;

import lombok.*;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.users.User;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Person")
@IdClass(Person.PersonId.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonId implements Serializable {
        String name;
        UUID user;
    }

    @Id
    @Setter(AccessLevel.NONE)
    String name;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Setter(AccessLevel.NONE)
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return name != null && Objects.equals(name, person.name)
                && user != null && Objects.equals(user, person.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
