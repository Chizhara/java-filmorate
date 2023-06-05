package ru.yandex.practicum.filmorate.model;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest
public class FilmControllerValidationTests {
    private static final Gson gson = GsonBuilder.getGson();
    static final String url = "http://localhost:8080/";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validateCorrectFilm() throws Exception {
        Film film = Film.builder().id(1).name("Фильм").durationInMinutes(5).
                releaseDate(LocalDate.of(2000, 1, 1)).description("Описание фильма").build();

        MvcResult result = mockMvc.perform(post(url + "/film").
                content(gson.toJson(film)).contentType("application/json")).
                andExpect(status().isOk()).andReturn();

        String filmJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(film, gson.fromJson(filmJson, Film.class), "Фильмы не сопадают");
    }

    @Test
    public void shouldNotValidateFilmWithDate1895dec27() throws Exception {
        Film film = Film.builder().id(1).name("Фильм").durationInMinutes(5).
                releaseDate(LocalDate.of(1895, 12, 27)).description("Описание фильма").build();

        mockMvc.perform(post(url + "/film").
                        content(gson.toJson(film)).contentType("application/json")).
                andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    public void shouldNotValidateFilmWithNullName() throws Exception {
        Film film = Film.builder().id(1).name(null).durationInMinutes(5).
                releaseDate(LocalDate.of(2000, 12, 27)).description("Описание фильма").build();

        MvcResult result = mockMvc.perform(post(url + "/film").
                        content(gson.toJson(film)).contentType("application/json")).
                andExpect(status().is4xxClientError()).andReturn();

        String filmJson = result.getResponse().getContentAsString();
        assertTrue(filmJson.isEmpty(), "Фильм добавился");
    }

    @Test
    public void shouldNotValidateFilmWithDescriptionLength201() throws Exception {
        Film film = Film.builder().id(1).name("Фильм").durationInMinutes(5).
                releaseDate(LocalDate.of(2000, 12, 27)).description("О".repeat(201)).build();

        mockMvc.perform(post(url + "/film").
                        content(gson.toJson(film)).contentType("application/json")).
                andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldNotValidateFilmWithNegativeDuration() throws Exception {
        Film film = Film.builder().id(1).name("Фильм").durationInMinutes(0).
                releaseDate(LocalDate.of(2000, 12, 27)).description("Описание фильма").build();

        mockMvc.perform(post(url + "/film").
                content(gson.toJson(film)).contentType("application/json")).
                andExpect(status().is4xxClientError());
    }
}
