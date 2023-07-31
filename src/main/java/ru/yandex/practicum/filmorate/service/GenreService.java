package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public Genre getMpaById(int genreId) {
        Optional<Genre> genreOptional = genreDao.getGenreById(genreId);
        if (genreOptional.isEmpty()) {
            throw new NoDataFoundException("Отсутствует Genre с id = " + genreId);
        }
        return genreOptional.get();
    }

    public List<Genre> getAllGenres() {
        return genreDao.getGenres();
    }
}
