package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Для FilmController:
 * добавление фильма;
 * обновление фильма;
 * получение всех фильмов.
 */
@RestController
@Slf4j
public class FilmController {

    private long id;
    private final Map<Long, Film> films = new HashMap<>();

    /**
     * возвращает список фильмов
     *
     * @return ArrayList
     */
    @GetMapping("/films")
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    /**
     * создает объект фильм и помещает в мап
     *
     * @param film        - film
     * @param releaseDate - date
     * @return film
     */
    @PostMapping("/films")
    public Film create(
            @RequestBody Film film,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate releaseDate) {

        log.info("POST request /film with data=" + film);

        filmsValidation(film);

        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    /**
     * выполняет валидацию фильма в зависимости от условий
     *
     * @param film
     */
    private void filmsValidation(Film film) {
        if (films.containsKey(film.getId())) {
            log.warn("POST request FILM, film exists" + film);
            throw new ValidationException("film exists" + film);
        } else if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("POST request FILM, name is empty, null " + film);
            throw new ValidationException("Films name is empty or null");
        } else if (film.getDescription().length() > 200) {
            log.warn("POST request FILM, description length exceeded 200. Must be less " + film);
            throw new ValidationException("Films description length exceeded 200. Must be less ");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("POST request FILM, release date is not valid " + film);
            throw new ValidationException("Films release date is not valid ");
        } else if (film.getDuration() <= 0) {
            log.warn("POST request FILM, duration must be positive " + film);
            throw new ValidationException("Films duration must be positive ");
        }

    }

    /**
     * Обновляет данные о фильме
     *
     * @param film      film
     * @param localDate date
     * @return - film
     */
    @PutMapping("/films")
    public Film updateFilm(
            @RequestBody Film film,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate localDate) {
        if (!films.containsKey(film.getId())) {
            log.warn("PUT request FILM, no such film" + film);
            throw new ValidationException("PUT request FILM, no such film");
        } else {
            log.warn("PUT request FILM, updating" + film);
            films.put(film.getId(), film);
        }
        return film;
    }
}
