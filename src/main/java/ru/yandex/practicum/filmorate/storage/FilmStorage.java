package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    public Film create(Film film);

    public Film update(Film film);

    public void delete(Film film);

    public List<Film> getFilms();

    Film getFilmById(Long filmId);
}