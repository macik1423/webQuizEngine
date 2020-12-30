package engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuizRepository quizRepository;

    @Test
    @WithMockUser
    @Transactional //odpalane wszystko w jednej transakcji, na koniec testu rollback
    void shouldGetSingleQuiz() throws Exception {
        //given
        Quiz newQuiz = new Quiz();
        newQuiz.setText("Test");
        newQuiz.setTitle("Kot");
        newQuiz.setOptions(Arrays.asList("pierwsza", "druga"));
        quizRepository.save(newQuiz);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/quizzes/" + newQuiz.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        assertThat(quiz).isNotNull();
        assertThat(quiz.getId()).isEqualTo(newQuiz.getId());
        assertThat(quiz.getTitle()).isEqualTo("Kot");
        assertThat(quiz.getText()).isEqualTo("Test");
        assertThat(quiz.getOptions()).hasSize(2);
    }

    @Test
    void shouldNotGetQuizWhenUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/api/quizzes/1"))
//                .andDo(print())
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser
    void shouldGetAllQuizzes() throws Exception {
        mockMvc.perform(get("/api/quizzes"))
//                .andDo(print())
                .andExpect(status().is(200));

    }
}