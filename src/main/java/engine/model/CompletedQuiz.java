package engine.model;

import com.fasterxml.jackson.annotation.*;
import net.bytebuddy.build.ToStringPlugin;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Component
public class CompletedQuiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_quiz_id")
    private UserQuiz completedBy;

    public CompletedQuiz() {
    }

    public long getId() {
        return id;
    }

    public UserQuiz getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(UserQuiz completedBy) {
        this.completedBy = completedBy;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
