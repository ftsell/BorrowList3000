package me.finnthorben.thingpeoplelist.things;

import lombok.*;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Thing")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Thing {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name="list_id")
    ThingList list;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    Person person;

    String name;

    @Setter
    String description;

    public Thing(String name, String description, ThingList list, Person person) {
        this.list = list;
        this.name = name;
        this.description = description;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Thing thing = (Thing) o;
        return id != null && Objects.equals(id, thing.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
