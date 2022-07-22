package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


/**
 * создание пользователя;
 * обновление пользователя;
 * получение списка всех пользователей.
 */
@RestController
@Slf4j
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Возвращает список пользователей
     *
     * @return ArrayList<User>
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * получить пользователя по id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        return userService.getUserById(id);
    }

    /**
     * создание пользователя
     *
     * @param user - user
     * @return user
     */

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Обновление данных пользователя при нахождении ID
     * в противном случае - генерация исключения
     *
     * @param user - user
     * @return user
     */
    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * удаляет пользователя
     *
     * @param user
     */
    @DeleteMapping("/users")
    public void removeUser(@Valid @RequestBody User user) {
        userService.removeUser(user);
    }

    /**
     * добавляет друга
     *
     * @param id
     * @param friendId
     */
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * удаляет друга у пользователя
     *
     * @param id
     * @param friendId
     */
    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void removeFriend(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long friendId) {
        userService.removeFriend(id, friendId);
    }


    /**
     * получает список друзей
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/users/{id}/friends")
    public List<User> getListFriends(@PathVariable @Min(1) Long id) {
        return userService.getListFriends(id);
    }

    /**
     * получает общих друзей
     *
     * @param id
     * @param otherId
     * @return
     */
    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> addCommonListFriends(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }


}
