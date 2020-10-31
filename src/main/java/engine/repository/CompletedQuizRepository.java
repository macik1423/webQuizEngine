package engine.repository;

import engine.model.CompletedQuiz;
import engine.model.CompletedQuizProjection;
import engine.model.UserQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Long> {

    @Query("Select q.id as id, c.completedAt as completedAt from CompletedQuiz c inner join c.quiz q where c.completedBy=?1")
    Page<CompletedQuizProjection> findAllCompleted(Pageable pageable, UserQuiz completedBy);

}
