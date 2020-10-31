package engine.service;

import engine.model.UserQuiz;
import engine.repository.UserQuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserQuizService {
    private UserQuizRepository userQuizRepository;
    private PasswordEncoder passwordEncoder;

    public UserQuizService(UserQuizRepository userQuizRepository, PasswordEncoder passwordEncoder) {
        this.userQuizRepository = userQuizRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserQuiz save(UserQuiz userQuiz) {
        String encryptedPassword = passwordEncoder.encode(userQuiz.getPassword());
        userQuiz.setPassword(encryptedPassword);
        if (userQuizRepository.existsByEmail(userQuiz.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userQuizRepository.save(userQuiz);
    }

    public Optional<UserQuiz> findByEmail(String email) {
        return userQuizRepository.findByEmail(email);
    }
}