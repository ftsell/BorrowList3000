package me.finnthorben.thingpeoplelist.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A Converter that is used to extract session tokens if they are present in HTTP requests.<br>
 *
 * This converter is integrated into spring security by defining its own {@link AuthenticationWebFilter} which
 * includes the converter.
 * The filter is provided to the application as a Bean.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SessionTokenAuthenticationConverter implements ServerAuthenticationConverter, ServerWebExchangeMatcher {

    final static String SESSION_TOKEN_HEADER = "Authorization";

    private final IAuthService authService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(SESSION_TOKEN_HEADER);
        return Mono.just(new SessionTokenAuthentication(authHeader));
    }

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        if (exchange.getRequest().getHeaders().containsKey(SESSION_TOKEN_HEADER)) {
            log.debug("ServerWebExchange matches to be processed by SessionTokenAuthenticationConverter");
            return MatchResult.match();
        }
        return MatchResult.notMatch();
    }

    @Bean
    @Qualifier("sessionTokenAuthenticationFilter")
    public static AuthenticationWebFilter sessionTokenAuthenticationFilter(ReactiveAuthenticationManager authManager, SessionTokenAuthenticationConverter instance) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
        filter.setRequiresAuthenticationMatcher(instance);
        filter.setServerAuthenticationConverter(instance);
        return filter;
    }
}
