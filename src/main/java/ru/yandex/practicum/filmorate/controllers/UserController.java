package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * создание пользователя;
 * обновление пользователя;
 * получение списка всех пользователей.
 */
@RestController
public class UserController {

    private final HashMap<Integer, User> userHashMap  = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private static int id = 0;


    /**
     * Возвращает список пользователей
     * @return ArrayList<User>
     */
    @GetMapping("/users")
    public ArrayList<User> getUsers() {
        log.info("Get request get/Users");
        return new ArrayList<>(userHashMap.values());
    }

    /**
     *  Валидация и создание пользователя
     * @param user - user
     * @param localDate  - birthday
     * @return user
     */

    @PostMapping(value = "/users")
    public User create(
            @RequestBody User user,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate localDate) {

        log.info("POST request /user with data=" + user);
        if (userHashMap.containsKey(user.getId())){
            log.warn("POST request USER, user exists" + user);
            throw new ValidationException("user exists" + user);
        }else if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.warn("POST request USER, email is empty, null or has no @ symbol" + user);
            throw new ValidationException("email is empty, null or has no @ symbol" + user);
        } else if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.warn("POST request USER, login is empty, null or has a whitespace" + user);
            throw new ValidationException("Login is empty, null or has a whitespace");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("POST request USER, birthday must be in nowadays" + user);
            throw new ValidationException("birthday must be in nowadays");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            log.warn("POST request USER, name is empty or null. name=login" + user);
            user.setName(user.getLogin());
        }

        user.setId(++id);
        userHashMap.put(user.getId(),user);
        return user;
    }

    /**
     * Обновление данных пользователя при нахождении ID
     * в противном случае - генерация исключения
     * @param user - user
     * @param localDate -date
     * @return user
     */
    @PutMapping("/users")
    public User updateUser(
            @RequestBody User user,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate localDate) {

        log.info("PUT request with data=" + user);
        if (!userHashMap.containsKey(user.getId())) {
            log.warn("PUT request USER, no such user" + user);
           throw  new ValidationException("PUT request USER, no such user");
        } else {
            log.warn("PUT request USER, updating" + user);
           userHashMap.put(user.getId(),user);
        }
        return user;
    }


}
