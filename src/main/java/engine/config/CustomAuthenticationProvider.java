package engine.config;

import engine.model.UserQuiz;
import engine.service.UserQuizService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private UserQuizService userQuizService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(@Lazy UserQuizService userQuizService, @Lazy PasswordEncoder passwordEncoder) {
        this.userQuizService = userQuizService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserQuiz userQuiz = userQuizService.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean isSamePassword = passwordEncoder.matches(password, userQuiz.getPassword());
        if (!isSamePassword) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new UsernamePasswordAuthenticationToken(userQuiz, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}