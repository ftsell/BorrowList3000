package me.finnthorben.thingpeoplelist.api.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Override
    public User createUser(String username, String password, String email) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(new User(username, passwordEncoder.encode(password), email));
    }

    @Override
    public List<? extends Session> listAllSessionsOfUser(HttpSession session1) {
        String username = (String) session1.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        return sessionRepository.findByPrincipalName(username)
                .values()
                .stream()
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(null));
    }
}
