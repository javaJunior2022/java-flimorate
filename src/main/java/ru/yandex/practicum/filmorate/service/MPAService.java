package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPAStorage;

import java.util.List;

@Service
public class MPAService {
    private MPAStorage mpaStorage;

    @Autowired
    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<MPA> getAll() {
        return mpaStorage.getAll();
    }

     public MPA getMPA(Long id) throws EntityNotFoundException {
        if (id == null || id < 0) {
            throw new EntityNotFoundException("MPA ID is wrong");
        }
        return mpaStorage.getMPA(id);
    }
}
