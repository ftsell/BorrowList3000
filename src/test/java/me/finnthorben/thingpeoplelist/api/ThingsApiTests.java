package me.finnthorben.thingpeoplelist.api;

import me.finnthorben.thingpeoplelist.api.fixtures.PeopleFixture;
import me.finnthorben.thingpeoplelist.api.fixtures.ThingListFixture;
import me.finnthorben.thingpeoplelist.api.fixtures.UserFixture;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.people.Person;
import me.finnthorben.thingpeoplelist.people.dto.PersonDto;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.things.Thing;
import me.finnthorben.thingpeoplelist.things.ThingService;
import me.finnthorben.thingpeoplelist.things.dto.CreateThingRequest;
import me.finnthorben.thingpeoplelist.things.dto.ThingDto;
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
public class ThingsApiTests {
    @Autowired
    private UserFixture userFixture;

    @Autowired
    private PeopleFixture peopleFixture;

    @Autowired
    private ThingListFixture thingListFixture;

    @Autowired
    private ThingService thingService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebTestClient client;

    @Test
    void testGetAll() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        Pair<ThingList, Thing> listThingPair = thingListFixture.createThingListWithThing(userSessionPair.getFirst());

        // execution
        client
                .get()
                .uri("/api/lists/" + listThingPair.getFirst().getId() + "/things/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ThingDto.class)
                .isEqualTo(List.of(modelMapper.map(listThingPair.getSecond(), ThingDto.class)));
    }

    @Test
    void testCreate() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        Person person = peopleFixture.createPerson(userSessionPair.getFirst());
        ThingList list = thingListFixture.createEmptyThingList(userSessionPair.getFirst());

        // execution
        ThingDto response = client
                .post()
                .uri("/api/lists/" + list.getId() + "/things/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateThingRequest("TestThing", null, person.getId()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ThingDto.class)
                .returnResult().getResponseBody();
        assertThat(thingService.getByIdForUser(response.getId(), userSessionPair.getFirst()))
                .isNotNull();
    }
}
