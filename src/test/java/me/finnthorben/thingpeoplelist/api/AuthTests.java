package me.finnthorben.thingpeoplelist.api;

import me.finnthorben.thingpeoplelist.security.auth.AuthService;
import me.finnthorben.thingpeoplelist.security.dto.*;
import me.finnthorben.thingpeoplelist.security.sessions.SessionRepository;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.UserService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private WebTestClient client;

    @Test
    void testRegistration() {
        client
                .post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterRequest(null))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(SessionWithTokenDto.class);
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(sessionRepository.count()).isEqualTo(1);
    }

    @Test
    void testResourceProtection() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session = Objects.requireNonNull(sessionService.createSession(user, null, null).block());

        // execution (no auth)
        client
                .get()
                .uri("/api/users/me")
                .exchange()
                .expectStatus().isUnauthorized();

        // execution (with auth)
        client
                .get()
                .uri("/api/users/me")
                .header("Authorization", session.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testListSessions() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session = Objects.requireNonNull(sessionService.createSession(user, null, null).block());

        // execution
        client
                .get()
                .uri("/api/auth/sessions")
                .header("Authorization", session.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SessionDto.class)
                .consumeWith(result -> {
                    SessionDto responseSession = result.getResponseBody().stream().findFirst().orElseThrow();
                    assertThat(responseSession.getSessionId()).isEqualTo(session.getId().toString());
                    assertThat(responseSession.getIpAddress()).isEqualTo(session.getIpAddress());
                    assertThat(responseSession.getUserAgent()).isEqualTo(session.getUserAgent());
                    assertThat(responseSession.getCreationTime()).isNotNull();
                    assertThat(responseSession.getLastAccessTime()).isNotNull();
                    assertThat(responseSession.isCurrent()).isEqualTo(true);
                });
    }

    @Test
    void testLogoutAllSessionsExceptCurrent() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session = Objects.requireNonNull(sessionService.createSession(user, null, null).block());
        sessionService.createSession(user, null, null).block();

        // execution
        client
                .delete()
                .uri("/api/auth/sessions")
                .header("Authorization", session.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful();

        assertThat(sessionRepository.count()).isEqualTo(1);
    }

    @Test
    void testLogoutSpecificSession() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session1 = Objects.requireNonNull(sessionService.createSession(user, null, null).block());
        Session session2 = Objects.requireNonNull(sessionService.createSession(user, null, null).block());

        // execution
        client
                .delete()
                .uri("/api/auth/sessions/" + session2.getId().toString())
                .header("Authorization", session1.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful();

        assertThat(sessionRepository.count()).isEqualTo(1);
    }

    @Test
    void testNotLogoutCurrentSpecificSessionIfLastSession() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session1 = Objects.requireNonNull(sessionService.createSession(user, null, null).block());
        Session session2 = Objects.requireNonNull(sessionService.createSession(user, null, null).block());

        // execution (logout current session that is not the last one)
        client
                .delete()
                .uri("/api/auth/sessions/" + session2.getId().toString())
                .header("Authorization", session2.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful();
        assertThat(sessionRepository.count()).isEqualTo(1);

        // execution (try and fail to log out current session that is the last one)
        client
                .delete()
                .uri("/api/auth/sessions/" + session1.getId().toString())
                .header("Authorization", session1.getToken())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLoginToNewDevice() {
        // preparation
        User user = Objects.requireNonNull(userService.createUser(null).block());
        Session session1 = Objects.requireNonNull(sessionService.createSession(user, null, null).block());

        // execution
        PendingSessionDto prepareResponse = client
                .post()
                .uri("/api/auth/login/prepare")
                .header("Authorization", session1.getToken())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PendingSessionDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(prepareResponse).isNotNull();

        client
                .post()
                .uri("/api/auth/login/perform")
                .bodyValue(new LoginPerformRequest(prepareResponse.getToken()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(SessionWithTokenDto.class);
    }
}
