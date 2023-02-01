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
@Getter
@ToString
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    UUID id;

    @Setter
    String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    User user;

    public Person(String name, User user) {
        this.name = name;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return id != null && Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
