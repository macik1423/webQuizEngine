package engine.controller;

import engine.model.*;
import engine.service.CompletedQuizService;
import engine.service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class QuizController {

    private QuizService quizService;
    private CompletedQuizService completedQuizService;

    public QuizController(QuizService quizService, CompletedQuizService completedQuizService) {
        this.quizService = quizService;
        this.completedQuizService = completedQuizService;
    }

    @PostMapping(value = "/quizzes/{id}/solve", consumes = "application/json")
    public Feedback getFeedback(@RequestBody Map request, @PathVariable long id) {
        System.out.println("getFeedback");
        return quizService.getFeedback(request, id);
    }

    @GetMapping("/quizzes/{id}")
    public QuizSpecifiedProjection getQuizById(@PathVariable long id) {
        System.out.println("getQuizById");
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public Page<QuizSpecifiedProjection> getQuizzes(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        System.out.println("getQuizzes");
        return quizService.getQuizzes(page, pageSize);
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz newQuiz) {
        System.out.println("createQuiz");
        return quizService.createQuiz(newQuiz);
    }

    @DeleteMapping(value = "/quizzes/{id}")
    public void deleteQuiz(@PathVariable long id) {
        System.out.println("delete quiz");
        quizService.deleteQuiz(id);
    }

    @GetMapping(value = "/quizzes/completed")
    public Page<CompletedQuizProjection> getCompletedQuizzes(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        System.out.println("getCompletedQuizzes");
        return completedQuizService.getAll(page, pageSize);
    }
}