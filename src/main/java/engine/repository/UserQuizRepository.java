package engine.repository;

import engine.model.UserQuiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQuizRepository extends CrudRepository<UserQuiz, Long> {
    Boolean existsByEmail(String email);
    Optional<UserQuiz> findByEmail(String email);
}