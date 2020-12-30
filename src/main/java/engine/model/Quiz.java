package engine.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "completedQuizzes"}) //avoid infinity recursion
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> options;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> answer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<CompletedQuiz> completedQuizzes;

    @ManyToOne
    @JoinColumn(name = "USER_QUIZ_ID")
    private UserQuiz author;

    public Quiz() {
    }

    public Quiz(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public List<engine.model.CompletedQuiz> getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(List<engine.model.CompletedQuiz> completedQuizzes) {
        this.completedQuizzes = completedQuizzes;
    }

    @JsonIgnore
    public UserQuiz getAuthor() {
        return author;
    }
    @JsonSetter
    public void setAuthor(UserQuiz author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }

    @JsonSetter
    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @Value("#{target.id}")
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }
}