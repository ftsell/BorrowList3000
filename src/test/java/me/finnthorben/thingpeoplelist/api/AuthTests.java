package me.finnthorben.thingpeoplelist.api;

import me.finnthorben.thingpeoplelist.security.auth.AuthService;
import me.finnthorben.thingpeoplelist.security.dto.SessionDto;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.security.dto.LoginRequest;
import me.finnthorben.thingpeoplelist.security.dto.RegisterRequest;
import me.finnthorben.thingpeoplelist.users.UserService;
import me.finnthorben.thingpeoplelist.users.User;
import me.finnthorben.thingpeoplelist.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthTests {

    @Autowired
    private UserRepository userRepository;

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
                .bodyValue(new RegisterRequest("test", "test", null))
                .exchange()
                .expectStatus().is2xxSuccessful();
        assert userRepository.existsByUsernameIgnoreCase("test");
    }

    @Test
    void testLogin() {
        // preparation
        userService.createUser("test", "test", null);

        // execution
        client
                .post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LoginRequest("test", "test"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("authToken").exists();
    }

    @Test
    void testResourceProtection() {
        // preparation
        userService.createUser("test", "test", null);
        Session session = authService.login("test", "test", null, null).block();
        assertThat(session).isNotNull();

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
                .header("Authorization", session.getToken().toString())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testListSessions() {
        // preparation
        userService.createUser("test", "test", null);
        Session session = authService.login("test", "test", "::1", "test-agent").block();
        assertThat(session).isNotNull();

        // execution
        client
                .get()
                .uri("/api/auth/sessions")
                .header("Authorization", session.getToken().toString())
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
        userService.createUser("test", "test", null);
        Session session = authService.login("test", "test", null, null).block();
        authService.login("test", "test", null, null).block();
        assertThat(session).isNotNull();
    }

    @Test
    void testLogoutAllSessionsIncludingCurrent() {
        // preparation
        User user = userService.createUser("test", "test", null);
        Session session = authService.login("test", "test", null, null).block();
        authService.login("test", "test", null, null).block();
        assertThat(session).isNotNull();

        // execution
        client
                .delete()
                .uri("/api/auth/sessions?includingCurrent=true")
                .header("Authorization", session.getToken().toString())
                .exchange()
                .expectStatus().is2xxSuccessful();
        assertThat(sessionService.listAllSessionsOfUser(user).block()).isEmpty();
    }

    @Test
    void testLogoutSpecificSession() {
        // preparation
        User user = userService.createUser("test", "test", null);
        Session session = authService.login("test", "test", null, null).block();

        // execution
        client
                .delete()
                .uri("/api/auth/sessions/" + session.getId().toString())
                .header("Authorization", session.getToken().toString())
                .exchange()
                .expectStatus().is2xxSuccessful();
        assertThat(sessionService.listAllSessionsOfUser(user).block()).isEmpty();
    }
}
