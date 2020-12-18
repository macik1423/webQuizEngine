package engine.service;

import engine.config.IAuthenticationFacade;
import engine.model.Feedback;
import engine.model.QuizSpecifiedProjection;
import engine.model.UserQuiz;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizRepository;
import engine.repository.UserQuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {
    private QuizRepository quizRepository;
    private CompletedQuizRepository completedQuizRepository;
    private Feedback feedback;
    private IAuthenticationFacade authenticationFacade;
    private UserQuizRepository userQuizRepository;
    private UserQuiz userQuiz;

    public QuizService(QuizRepository quizRepository, CompletedQuizRepository completedQuizRepository, engine.model.Feedback feedback, IAuthenticationFacade authenticationFacade, UserQuizRepository userQuizRepository, engine.model.UserQuiz userQuiz) {
        this.quizRepository = quizRepository;
        this.completedQuizRepository = completedQuizRepository;
        this.feedback = feedback;
        this.authenticationFacade = authenticationFacade;
        this.userQuizRepository = userQuizRepository;
        this.userQuiz = userQuiz;
    }

    public engine.model.Feedback getFeedback(Map request, long id) {
        engine.model.UserQuiz userQuiz = getLoggedUserQuiz();
        engine.model.Quiz quiz = quizRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        quiz.setAnswer(quiz.getAnswer() == null ? new ArrayList<>() : quiz.getAnswer());
        List<Integer> requestAnswer = (List<Integer>) request.get("answer");

        if (checkAnswers(requestAnswer, quiz)) {
            feedback.setSuccess(true);
            feedback.setFeedback("Congratulations, you're right!");
            engine.model.CompletedQuiz completedQuiz = new engine.model.CompletedQuiz();
            completedQuiz.setQuiz(quiz);
            completedQuiz.setCompletedAt(LocalDateTime.now());
            completedQuiz.setCompletedBy(userQuiz);
            completedQuizRepository.save(completedQuiz);
        } else {
            feedback.setSuccess(false);
            feedback.setFeedback("Wrong answer! Please, try again.");
        }

        return feedback;
    }

    private engine.model.UserQuiz getLoggedUserQuiz() {
        Object auth = authenticationFacade.getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Object loggedUser = authenticationFacade.getAuthentication().getPrincipal();
        return userQuizRepository.findById(((engine.model.UserQuiz) loggedUser).getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private boolean checkAnswers(List<Integer> requestAnswer, engine.model.Quiz quiz) {
        return quiz.getAnswer().containsAll(requestAnswer) && requestAnswer.containsAll(quiz.getAnswer());
    }

    public QuizSpecifiedProjection getQuizById(Long id) {
        QuizSpecifiedProjection quiz = quizRepository.findQuizSpecifiedById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return quiz;
    }

    public Page<engine.model.QuizSpecifiedProjection> getQuizzes(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return quizRepository.findQuizSpecifiedAll(paging);
    }

    public engine.model.Quiz createQuiz(engine.model.Quiz newQuiz) {
        engine.model.UserQuiz userQuiz = getLoggedUserQuiz();
        newQuiz.setAuthor(userQuiz);
        quizRepository.save(newQuiz);
        return newQuiz;
    }

    public void deleteQuiz(long id) {
        engine.model.Quiz quiz = quizRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        engine.model.UserQuiz userQuiz = getLoggedUserQuiz();
        if (userQuiz.getEmail().equals(quiz.getAuthor().getEmail())) {
            quizRepository.delete(quiz);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
