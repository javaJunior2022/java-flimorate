package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> getAll();

    Genre getGenre(Long id);

    void createGenre(Long film_id, Long genre_id);

    void removeGenre(Long id);

}
