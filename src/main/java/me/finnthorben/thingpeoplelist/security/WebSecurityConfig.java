package me.finnthorben.thingpeoplelist.security;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.security.auth.SessionTokenAuthenticationManager;
import me.finnthorben.thingpeoplelist.security.sessions.SessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            @Qualifier("sessionTokenAuthenticationFilter") AuthenticationWebFilter sessionTokenFilter) {
        return http
                .authorizeExchange()
                .pathMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .pathMatchers("/api/schema/**", "/api/swagger-ui/**", "/api/webjars/swagger-ui/**").permitAll()
                .pathMatchers("/api/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .addFilterAt(sessionTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(
            ReactiveUserDetailsService userDetailsService,
            SessionService sessionService) {
        return new DelegatingReactiveAuthenticationManager(
                new SessionTokenAuthenticationManager(sessionService),
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}