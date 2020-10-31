package engine.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public class UserQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = ".*@.*\\..+")
    private String email;

    @Size(min = 5)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
    private List<engine.model.Quiz> createdQuizzes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "completedBy", fetch = FetchType.LAZY)
    private List<engine.model.CompletedQuiz> completedQuizzes;

    public UserQuiz() {
    }

    public long getId() {
        return id;
    }

    public List<engine.model.CompletedQuiz> getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(List<engine.model.CompletedQuiz> completedQuizzes) {
        this.completedQuizzes = completedQuizzes;
    }

    public List<engine.model.Quiz> getCreatedQuizzes() {
        return createdQuizzes;
    }

    public void setCreatedQuizzes(List<engine.model.Quiz> createdQuizzes) {
        this.createdQuizzes = createdQuizzes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserQuiz{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
