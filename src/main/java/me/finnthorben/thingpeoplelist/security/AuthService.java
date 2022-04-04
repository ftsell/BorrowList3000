package me.finnthorben.thingpeoplelist.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authManager;
    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Override
    public HttpSession login(Supplier<HttpSession> sessionSupplier, SessionInfo info, String username, String password) {
        // authenticate the user
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        // persist the authenticated security context into the session
        HttpSession session = sessionSupplier.get();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        session.setAttribute(SessionInfo.SESSION_INFO_INDEX_NAME, info);

        return session;
    }

    @Override
    public void logout(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
