package me.finnthorben.thingpeoplelist.lists;

import lombok.*;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.users.User;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ThingList")
@Getter
@ToString
@NoArgsConstructor
public class ThingList {
    @Id
    @GeneratedValue
    UUID id;

    @NonNull
    @Setter
    String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
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
        return id != null && Objects.equals(id, thingList.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
