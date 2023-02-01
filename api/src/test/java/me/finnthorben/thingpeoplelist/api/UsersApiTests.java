package me.finnthorben.thingpeoplelist.api;

import me.finnthorben.thingpeoplelist.api.fixtures.UserFixture;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UsersApiTests {
    @Autowired
    private UserFixture userFixture;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebTestClient client;

    @Test
    void testGetMe() {
        // preparation
        Pair<User, Session> userSessionPair = userFixture.createUserWithSession();

        // execution
        client
                .get()
                .uri("/api/users/me")
                .header("Authorization", userSessionPair.getSecond().getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserDto.class)
                .isEqualTo(modelMapper.map(userSessionPair.getFirst(), UserDto.class));
    }
}
