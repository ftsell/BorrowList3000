package me.finnthorben.thingpeoplelist.lists;

import lombok.*;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.users.User;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "List")
@IdClass(ThingList.ListId.class)
@Getter
@ToString
@NoArgsConstructor
public class ThingList {
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListId implements Serializable {
        String name;
        UUID user;
    }

    @Id
    String name;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "list")
    @ToString.Exclude
    Set<Thing> things;

    public ThingList(String name, User user) {
        this.name = name;
        this.user = user;
        this.things = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ThingList thingList = (ThingList) o;
        return name != null && Objects.equals(name, thingList.name)
                && user != null && Objects.equals(user, thingList.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
