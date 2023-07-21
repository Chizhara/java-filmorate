package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({GenreDaoImpl.class})
public class GenreDaoTest {
    static List<Genre> genres;
    @Autowired
    GenreDao genreDao;

    @BeforeAll
    public static void beforeAll() {
        genres = List.of(new Genre(1, "Комедия"), new Genre(2, "Драма"),
                new Genre(3, "Мультфильм"), new Genre(4, "Триллер"),
                new Genre(5, "Документальный"), new Genre(6, "Боевик"));
    }

    @Test
    @Order(1)
    public void shouldReturnCorrectGenresList() {
        List<Genre> genresTest = genreDao.getGenres();
        assertEquals(genresTest, genres);
    }

    @Test
    @Order(2)
    public void shouldReturnComedyGenre() {
        Optional<Genre> genreTest = genreDao.getGenreById(1);
        assertTrue(genreTest.isPresent());
        assertEquals(genreTest.get(), genres.get(0));
    }

    @Test
    @Order(3)
    public void shouldReturnCorrectRawFilmGenresData() {
        Map<Integer, List<Integer>> rawFilmGenresData = genreDao.getRawFilmIdWithGenresIds();
        Map<Integer, List<Integer>> rawFilmGenresTestData = Map.of(1, List.of(1));
        assertEquals(rawFilmGenresTestData, rawFilmGenresData);
    }

    @Test
    @Order(4)
    public void shouldRemoveGenreFromFilm() {
        genreDao.removeFilmGenresByFilmId(1);
        Map<Integer, List<Integer>> rawFilmGenresData = genreDao.getRawFilmIdWithGenresIds();
        Map<Integer, List<Integer>> rawFilmGenresTestData = Map.of();
        assertEquals(rawFilmGenresTestData, rawFilmGenresData);
    }

    @Test
    @Order(5)
    public void shouldAddGenreToFilm() {
        genreDao.addGenreToFilmById(1, 1);
        Map<Integer, List<Integer>> rawFilmGenresData = genreDao.getRawFilmIdWithGenresIds();
        Map<Integer, List<Integer>> rawFilmGenresTestData = Map.of(1, List.of(1));
        assertEquals(rawFilmGenresTestData, rawFilmGenresData);
    }
}
