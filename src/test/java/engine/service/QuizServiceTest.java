package engine.service;

import engine.model.QuizSpecifiedProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Test
    void shouldGetSingleQuiz() {
        //given

        //when
        QuizSpecifiedProjection quizById = quizService.getQuizById(1L);

        //then
        assertThat(quizById).isNotNull();
        assertThat(quizById.getId()).isEqualTo(1L);
    }
}