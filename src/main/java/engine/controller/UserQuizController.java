package engine.controller;

import engine.repository.UserQuizRepository;
import engine.model.Quiz;
import engine.model.UserQuiz;
import engine.service.UserQuizService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserQuizController {
    private final UserQuizService userQuizService;

    public UserQuizController(UserQuizService userQuizService) {
        this.userQuizService = userQuizService;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public UserQuiz registerUser(@Valid @RequestBody UserQuiz userQuiz) {
        return userQuizService.save(userQuiz);
    }

    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }
}
