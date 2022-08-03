package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@RestController
@Slf4j
@Validated

public class MPAController {
    private  MPAService mpaService;

    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public List<MPA> getAll() {
        return mpaService.getAll();
    }
    @GetMapping(value = "/mpa/{id}")
    public MPA getMPA(@PathVariable Long id) {
        return mpaService.getMPA(id);
    }
}
