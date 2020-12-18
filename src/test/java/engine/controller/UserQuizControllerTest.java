package engine.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserQuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    void shouldRegisterAndGetContent() throws Exception {
        //given
        String email = "maciejpaciej@com.pl";
        String password = "12345";
        JSONObject credentials = new JSONObject();
        credentials.put("email", email);
        credentials.put("password", password);
        //when
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(credentials.toString()))
                .andExpect(status().is(200));

        mockMvc.perform(get("/api/secured").with(httpBasic(email, password)))
                .andExpect(status().is(200)).andExpect(content().string("secured"));

        mockMvc.perform(get("/api/secured")).andExpect(status().is(401));
    }
}