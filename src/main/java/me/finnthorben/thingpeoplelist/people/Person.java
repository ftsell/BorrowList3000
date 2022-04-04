package me.finnthorben.thingpeoplelist.people;

import lombok.*;
import me.finnthorben.thingpeoplelist.users.User;

import javax.persistence.*;
import java.io.Serializable;
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
    @Getter
    public static class PersonId implements Serializable {
        String name;
        UUID user;
    }

    @Id
    String name;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
