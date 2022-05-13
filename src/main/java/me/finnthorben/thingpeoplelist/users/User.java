package me.finnthorben.thingpeoplelist.users;

import lombok.*;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(name = "uc_email", columnNames = {"email"})
})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    UUID id;

    @Nullable
    @Email
    String email;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<Person> people;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<ThingList> lists;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<Session> sessions;

    public User(@Nullable String email) {
        this.email = email;
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
