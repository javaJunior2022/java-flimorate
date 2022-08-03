package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private GenreStorage genreStorage;

    @Autowired
    public  GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public Genre getGenre(Long id) throws EntityNotFoundException {
        if (id == null || id < 0) {
            throw new EntityNotFoundException("No genre with this id");
        }
        return genreStorage.getGenre(id);
    }


}
