package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * возвращает список фильмов
     *
     * @return ArrayList
     */
    @GetMapping("/films")
    public List<Film> getFilms() {
        return new ArrayList<>(filmService.getFilms());
    }

    /**
     * Получение фильма по Id
     *
     * @param filmId
     * @return
     */
    @GetMapping("/films/{filmId}")
    public Film getFilmById(@PathVariable @Min(1) Long filmId) {
        return filmService.getFilmById(filmId);
    }

    /**
     * создает объект фильм
     *
     * @param film - film
     * @return film
     */
    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    /**
     * Обновляет данные о фильме
     *
     * @param film film
     * @return - film
     */
    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    /**
     * удаляет фильм
     *
     * @param film
     */
    @DeleteMapping(value = "/films")
    public void remove(@Valid @RequestBody Film film) {
        filmService.remove(film);
    }

    /**
     * Добавляет лайк к фильму
     *
     * @param id
     * @param userId
     * @return
     */

    @PutMapping(value = "/films/{id}/like/{userId}")
    public Integer addLike(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    /**
     * убирает лайк
     *
     * @param id
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public Integer removeLike(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return filmService.removeLike(filmService.getFilmById(id).getId(), userId);
    }

    /**
     * получает топ фильмов
     *
     * @param count
     * @return
     */
    @GetMapping(value = "/films/popular")
    public List<Film> getTopLikeFilm(
            @RequestParam(defaultValue = "10", required = false) Integer count) {
        return filmService.getTopLike(count);
    }
}
