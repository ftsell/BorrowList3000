package me.finnthorben.thingpeoplelist.api;


import me.finnthorben.thingpeoplelist.api.fixtures.ThingListFixture;
import me.finnthorben.thingpeoplelist.api.fixtures.UserFixture;
import me.finnthorben.thingpeoplelist.lists.ThingList;
import me.finnthorben.thingpeoplelist.lists.ThingListService;
import me.finnthorben.thingpeoplelist.lists.dto.CreateThingListRequest;
import me.finnthorben.thingpeoplelist.lists.dto.PatchThingListRequest;
import me.finnthorben.thingpeoplelist.lists.dto.ThingListDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ThingListsApiTests {
    @Autowired
    private UserFixture userFixture;

    @Autowired
    private ThingListFixture thingListFixture;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebTestClient client;

    @Autowired
    private ThingListService thingListService;

    @Test
    void testGetAll() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        ThingList thingList = thingListFixture.createEmptyThingList(userSessionPair.getFirst());

        // execution
        client
                .get()
                .uri("/api/lists/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(ThingListDto.class)
                .isEqualTo(List.of(modelMapper.map(thingList, ThingListDto.class)));
    }

    @Test
    void testGetSpecificList() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        ThingList thingList = thingListFixture.createEmptyThingList(userSessionPair.getFirst());

        // execution
        client
                .get()
                .uri("/api/lists/" + thingList.getId())
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ThingListDto.class)
                .isEqualTo(modelMapper.map(thingList, ThingListDto.class));
    }

    @Test
    void testCreate() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();

        // execution
        ThingListDto response = client
                .post()
                .uri("/api/lists/")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateThingListRequest("TestList"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ThingListDto.class)
                .returnResult().getResponseBody();
        assertThat(thingListService.getByIdForUser(response.getId(), userSessionPair.getFirst()).block())
                .isNotNull();
    }

    @Test
    void testUpdateListName() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        ThingList thingList = thingListFixture.createEmptyThingList(userSessionPair.getFirst());

        // execution
        WebTestClient.ResponseSpec response = client
                .patch()
                .uri("/api/lists/" + thingList.getId())
                .header("Authorization", userSessionPair.getSecond().getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PatchThingListRequest("OtherList"))
                .exchange();

        ThingList updatedThingList = thingListService.getByIdForUser(thingList.getId(), userSessionPair.getFirst()).block();
        response
                .expectStatus().is2xxSuccessful()
                .expectBody(ThingListDto.class)
                .isEqualTo(modelMapper.map(updatedThingList, ThingListDto.class));
    }

    @Test
    void delete() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();
        ThingList thingList = thingListFixture.createEmptyThingList(userSessionPair.getFirst());

        // execution
        client
                .delete()
                .uri("/api/lists/" + thingList.getId())
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful();
        assertThatThrownBy(() -> thingListService.getByIdForUser(thingList.getId(), userSessionPair.getFirst()).block())
                .isInstanceOf(ThingListService.NoSuchThingListException.class);
    }
}
