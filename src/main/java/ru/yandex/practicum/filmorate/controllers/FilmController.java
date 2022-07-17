package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Для FilmController:
 * добавление фильма;
 * обновление фильма;
 * получение всех фильмов.
 */
@RestController
public class FilmController {
    private int id;
    private final HashMap<Integer, Film> filmHashMap = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    /**
     * возвращает список фильмов
     *
     * @return ArrayList
     */
    @GetMapping("/films")
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(filmHashMap.values());
    }

    /**
     * создает объект фильм и помещает в мап
     *
     * @param film        - flim
     * @param releaseDate - date
     * @return film
     */
    @PostMapping("/films")
    public Film create(
            @RequestBody Film film,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate releaseDate) {

        log.info("POST request /film with data=" + film);
        if (filmHashMap.containsKey(film.getId())) {
            log.warn("POST request FILM, film exists" + film);
            throw new ValidationException("film exists" + film);
        } else if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("POST request FILM, name is empty, null " + film);
            throw new ValidationException("Films name is empty or null");
        } else if (film.getDescription().length() > 200) {
            log.warn("POST request FILM, description length exceeded 200. Must be less " + film);
            throw new ValidationException("Films description length exceeded 200. Must be less ");
        } else if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("POST request FILM, release date is not valid " + film);
            throw new ValidationException("Films release date is not valid ");
        } else if (film.getDuration() <= 0) {
            log.warn("POST request FILM, duration must be positive " + film);
            throw new ValidationException("Films duration must be positive ");
        }
        film.setId(++id);
        filmHashMap.put(film.getId(), film);
        return film;
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
        if (!filmHashMap.containsKey(film.getId())) {
            log.warn("PUT request FILM, no such film" + film);
            throw new ValidationException("PUT request FILM, no such film");
        } else {
            log.warn("PUT request FILM, updating" + film);
            filmHashMap.put(film.getId(), film);
        }
        return film;
    }
}
