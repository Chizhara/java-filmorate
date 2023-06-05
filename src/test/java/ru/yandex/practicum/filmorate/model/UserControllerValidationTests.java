package ru.yandex.practicum.filmorate.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerValidationTests {
    private static final Gson gson = GsonBuilder.getGson();
    static final String url = "http://localhost:8080/";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validateCorrectUser() throws Exception {
        User user = User.builder().id(1).name("User").email("asd@email.com").login("login").
                birthdate(LocalDate.of(2000,1,1)).build();

        MvcResult result = mockMvc.perform(post(url + "/user").
                        content(gson.toJson(user)).contentType("application/json")).
                andExpect(status().isOk()).andReturn();
        String userJson = result.getResponse().getContentAsString();
        assertEquals(user, gson.fromJson(userJson, User.class), "Пользователи не сопадают");
    }

    @Test
    public void shouldReturnUserWithLoginInName() throws Exception {
        User user = User.builder().id(1).name(null).email("asd@email.com").login("login").
                birthdate(LocalDate.of(2000,1,1)).build();

        MvcResult result = mockMvc.perform(post(url + "/user").
                        content(gson.toJson(user)).contentType("application/json")).
                andExpect(status().isOk()).andReturn();

        user.setName(user.getLogin());
        String userJson = result.getResponse().getContentAsString();
        assertEquals(user, gson.fromJson(userJson, User.class), "Пользователи не сопадают");
    }

    @Test
    public void shouldNotValidateUserWithBirthdateInFuture() throws Exception {
        User user = User.builder().id(1).name("User").email("asd@email.com").login("login").
                birthdate(LocalDate.now().plusYears(1)).build();

        MvcResult result = mockMvc.perform(post(url + "/user").
                        content(gson.toJson(user)).contentType("application/json")).
                andExpect(status().is4xxClientError()).andReturn();

        String userJson = result.getResponse().getContentAsString();
        assertTrue(userJson.isEmpty(), "Пользователь добавился");
    }

    @Test
    public void shouldNotValidateUserWithWorseEmail() throws Exception {
        User user = User.builder().id(1).name("User").email("asd@").login("login").
                birthdate(LocalDate.of(2000,1,1)).build();

        MvcResult result = mockMvc.perform(post(url + "/user").
                        content(gson.toJson(user)).contentType("application/json")).
                andExpect(status().is4xxClientError()).andReturn();

        String userJson = result.getResponse().getContentAsString();
        assertTrue(userJson.isEmpty(), "Пользователь добавился");
    }

    @Test
    public void shouldNotValidateUserWithEmptyLogin() throws Exception {
        User user = User.builder().id(1).name("User").email("asd@email.com").login(" ").
                birthdate(LocalDate.of(2000,1,1)).build();

        MvcResult result = mockMvc.perform(post(url + "/user").
                        content(gson.toJson(user)).contentType("application/json")).
                andExpect(status().is4xxClientError()).andReturn();

        String userJson = result.getResponse().getContentAsString();
        assertTrue(userJson.isEmpty(), "Пользователь добавился");
    }
}
