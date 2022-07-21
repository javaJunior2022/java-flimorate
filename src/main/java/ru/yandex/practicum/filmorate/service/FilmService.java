package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    /**
     * Получение списка фильмов
     *
     * @return
     */
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    /**
     * получение фильмао по ID
     *
     * @param filmId
     * @return
     */
    public Film getFilmById( Long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    /**
     * Создание фильма
     *
     * @param film
     * @return
     */
    public Film create(Film film) {
        if (film == null) {
            throw new ValidationException("Film is null");
        }
        return filmStorage.create(film);
    }

    /**
     * Обновление фильма
     *
     * @param film
     * @return
     */
    public Film update(Film film) {
        if (filmStorage.getFilmById(film.getId()) == null) {
            throw new EntityNotFoundException("Film not found");
        }
        return filmStorage.update(film);
    }

    /**
     * Удаление фильма
     *
     * @param film
     * @throws ValidationException
     */
    public void remove(Film film) throws ValidationException {
        if (filmStorage.getFilmById(film.getId()) == null) {
            throw new ValidationException("Film not found");
        }
        filmStorage.delete(film);
    }

    /**
     * Добавление лайка к фильму
     *
     * @param filmId
     * @param userId
     * @return
     */
    public Integer addLike(Long filmId, Long userId) {
        if (filmStorage.getFilmById(filmId).getLikes().contains(userId)) {
            throw new ValidationException("User put like before");
        }
        filmStorage.getFilmById(filmId).getLikes().add(userId);

        return filmStorage.getFilmById(filmId).getLikes().size();
    }

    /**
     * удаление лайка
     *
     * @param filmId
     * @param userId
     * @return
     */
    public Integer removeLike(Long filmId, Long userId) {
        if (!filmStorage.getFilmById(filmId).getLikes().contains(userId)) {
            throw new EntityNotFoundException("No like with this user");
        }

        filmStorage.getFilmById(filmId).getLikes().remove(userId);

        return filmStorage.getFilmById(filmId).getLikes().size();
    }

    /**
     * Получить топ n верхних лайков
     *
     * @param count
     * @return
     */
    public List<Film> getTopLike(Integer count) {
        return filmStorage.getFilms().stream().sorted((p0, p1) ->
                        p1.getLikes().size() - (p0.getLikes().size())).
                limit(count).collect(Collectors.toList());
    }
}