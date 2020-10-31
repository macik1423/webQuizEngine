package engine.controller;

import engine.repository.UserQuizRepository;
import engine.model.Quiz;
import engine.model.UserQuiz;
import engine.service.UserQuizService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserQuizController {
    private UserQuizRepository userQuizRepository;
    private UserQuizService userQuizService;

    public UserQuizController(UserQuizRepository userQuizRepository, UserQuizService userQuizService) {
        this.userQuizRepository = userQuizRepository;
        this.userQuizService = userQuizService;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public UserQuiz registerUser(@Valid @RequestBody UserQuiz userQuiz) {
        System.out.println("registerUser");
        return userQuizService.save(userQuiz);
    }
}
