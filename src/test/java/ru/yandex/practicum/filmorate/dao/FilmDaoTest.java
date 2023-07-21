package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.MpaRateDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({FilmDaoImpl.class, GenreDaoImpl.class, MpaRateDaoImpl.class})
public class FilmDaoTest {
    private static Film filmA;
    private static Film filmB;
    private static Film filmC;
    @Autowired
    FilmDaoImpl filmDao;
    @Autowired
    GenreDao genreDao;

    @BeforeEach
    public void beforeEach() {
        filmA = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .genres(List.of(new Genre(1, "Комедия")))
                .mpa(new MpaRate(1, "G"))
                .build();

        filmC = Film.builder()
                .id(2)
                .name("nisi")
                .description("dip")
                .releaseDate(LocalDate.of(1968, 4, 26))
                .duration(101)
                .genres(List.of(new Genre(4, "Триллер"), new Genre(5, "Документальный")))
                .mpa(new MpaRate(1, "G"))
                .build();

        filmB = Film.builder()
                .id(3)
                .name("eiusmod nisi")
                .description("desciprt")
                .releaseDate(LocalDate.of(1976, 6, 7))
                .duration(120)
                .genres(List.of(new Genre(5, "Документальный"), new Genre(6, "Боевик")))
                .mpa(new MpaRate(2, "PG"))
                .build();
    }

    @Test
    public void shouldReturn2AfterAddFilm() {
        int id = filmDao.addFilm(filmB);
        assertEquals(3, id);
    }

    @Test
    public void shouldReturnFilmDataSearchedById() {
        Optional<Film> filmOptional = filmDao.findFilmById(1);
        assertTrue(filmOptional.isPresent());
        Film film = filmOptional.get();
        film.setGenres(genreDao.findGenresByFilmId(film.getId()));
        assertEquals(filmA, film);
    }

    @Test
    public void shouldUpdateFilmData() {
        filmDao.updateFilm(filmC);
        Optional<Film> filmOptional = filmDao.findFilmById(2);
        for (Genre genre : filmC.getGenres()) {
            genreDao.addGenreToFilmById(filmC.getId(), genre.getId());
        }
        assertTrue(filmOptional.isPresent());
        Film film = filmOptional.get();
        film.setGenres(genreDao.findGenresByFilmId(film.getId()));
        assertEquals(filmC, film);
    }

    @Test
    public void shouldReturnPrioritizedFilms() {
        Film filmFirst = filmDao.findFilmById(1).orElse(null);
        List<Film> films = filmDao.getFilmsRating(2);
        assertEquals(2, films.size());
        assertEquals(films.get(0), filmFirst);
    }
}
