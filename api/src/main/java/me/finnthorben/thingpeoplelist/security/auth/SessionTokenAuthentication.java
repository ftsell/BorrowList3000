package me.finnthorben.thingpeoplelist.security.auth;

import lombok.Getter;
import lombok.ToString;
import me.finnthorben.thingpeoplelist.security.sessions.Session;
import me.finnthorben.thingpeoplelist.users.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * An <tt>Authentication</tt> implementation that represents a session token.<br>
 *
 * It is usually automatically created as part of the security filter chain defined in
 * {@link SessionTokenAuthenticationConverter#sessionTokenAuthenticationFilter(ReactiveAuthenticationManager, SessionTokenAuthenticationConverter) SessionTokenAuthenticationConverter}
 * and handled by {@link SessionTokenAuthenticationManager}.
 */
@ToString
public class SessionTokenAuthentication extends AbstractAuthenticationToken {

    @NonNull
    @ToString.Exclude
    private final String token;

    @Nullable
    @Getter
    private final Session session;

    public SessionTokenAuthentication(String token) {
        this(token, null, null);
    }

    public SessionTokenAuthentication(String token, Session session, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.session = session;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        if (session != null)
            return session.getUser();
        return null;
    }

    @Nullable
    public User getUser() {
        if (session != null)
            return session.getUser();
        return null;
    }
}
