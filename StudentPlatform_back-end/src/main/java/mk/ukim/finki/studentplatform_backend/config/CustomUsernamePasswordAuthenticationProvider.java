package mk.ukim.finki.studentplatform_backend.config;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.models.User;
import mk.ukim.finki.studentplatform_backend.service.UserService;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public CustomUsernamePasswordAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder, HttpSession session) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.session = session;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("".equals(username) || "".equals(password)) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails = this.userService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password is incorrect!");
        }

        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        // Store the authenticated user in the session
        User user = null;
        try {
            user = this.userService.findByUsername(username);
        } catch (InvalidCredentialsException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("user", user);

        return authenticatedUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}