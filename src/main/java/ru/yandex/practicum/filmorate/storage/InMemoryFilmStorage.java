package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private long id = 0;
    private final Map<Long, Film> films = new HashMap<>();

    /**
     * возвращает список фильмов
     *
     * @return ArrayList
     */
    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
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
     * @param film film
     * @return - film
     */
    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("PUT request FILM, no such film" + film);
            throw new ValidationException("PUT request FILM, no such film");
        } else {
            log.warn("PUT request FILM, updating" + film);
            films.put(film.getId(), film);
        }
        return film;
    }

    /**
     * удаляет фильм
     *
     * @param film film
     * @return - film
     */
    @Override
    public void delete(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("PUT request FILM, no such film");
        } else {
            log.warn("delete", film);
            films.remove(film.getId());
        }
    }

    /**
     * получение фильма по id
     *
     * @param filmId
     * @return
     */
    @Override
    public Film getFilmById(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new EntityNotFoundException("Film not found");
        }
        return films.get(filmId);
    }


}