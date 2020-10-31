package engine.repository;

import engine.model.Quiz;
import engine.model.QuizSpecifiedProjection;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    @Query(value = "SELECT q " +
            "FROM Quiz q")
    Page<QuizSpecifiedProjection> findQuizSpecifiedAll(Pageable pageable);

    Optional<QuizSpecifiedProjection> findQuizSpecifiedById(long id);
}

