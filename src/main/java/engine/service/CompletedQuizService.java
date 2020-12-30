package engine.service;

import engine.config.IAuthenticationFacade;
import engine.model.CompletedQuizProjection;
import engine.model.UserQuiz;
import engine.repository.CompletedQuizRepository;
import engine.repository.UserQuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompletedQuizService {

    private final CompletedQuizRepository completedQuizRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final UserQuizRepository userQuizRepository;

    public CompletedQuizService(CompletedQuizRepository completedQuizRepository, IAuthenticationFacade authenticationFacade, UserQuizRepository userQuizRepository) {
        this.completedQuizRepository = completedQuizRepository;
        this.authenticationFacade = authenticationFacade;
        this.userQuizRepository = userQuizRepository;
    }

    public Page<CompletedQuizProjection> getAll(int pageNo, int pageSize) {
        Object auth = authenticationFacade.getAuthentication().getPrincipal();
        UserQuiz userQuiz = userQuizRepository.findById(((UserQuiz) auth).getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "completedAt"));
        return completedQuizRepository.findAllCompleted(paging, userQuiz);
    }
}
