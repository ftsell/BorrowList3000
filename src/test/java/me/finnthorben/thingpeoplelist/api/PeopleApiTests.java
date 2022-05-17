package me.finnthorben.thingpeoplelist.api;

import me.finnthorben.thingpeoplelist.api.fixtures.PeopleFixture;
import me.finnthorben.thingpeoplelist.api.fixtures.ThingListFixture;
import me.finnthorben.thingpeoplelist.api.fixtures.UserFixture;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.lists.dto.ThingListDto;
import me.finnthorben.thingpeoplelist.people.PeopleService;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.people.dto.CreatePersonRequest;
import me.finnthorben.thingpeoplelist.people.dto.PersonDto;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PeopleApiTests {
    @Autowired
    private UserFixture userFixture;

    @Autowired
    private PeopleFixture peopleFixture;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebTestClient client;

    @Autowired
    private PeopleService peopleService;

    @Test
    void testGetAll() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        Person person = peopleFixture.createPerson(userSessionPair.getFirst());

        // execution
        client
                .get()
                .uri("/api/people/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PersonDto.class)
                .isEqualTo(List.of(modelMapper.map(person, PersonDto.class)));
    }

    @Test
    void testGetGetSpecificPerson() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        Person person = peopleFixture.createPerson(userSessionPair.getFirst());

        // execution
        client
                .get()
                .uri("/api/people/" + person.getId())
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PersonDto.class)
                .isEqualTo(modelMapper.map(person, PersonDto.class));
    }

    @Test
    void testCreate() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();

        // execution
        PersonDto response = client
                .post()
                .uri("/api/people/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreatePersonRequest("TestPerson"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PersonDto.class)
                .returnResult().getResponseBody();
        assertThat(peopleService.getByIdForUser(response.getId(), userSessionPair.getFirst()).block())
                .isNotNull();
    }
}
