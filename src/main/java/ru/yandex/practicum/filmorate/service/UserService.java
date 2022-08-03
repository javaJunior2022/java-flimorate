package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {

    private UserStorage userStorage;


    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /**
     * Получение списка пользователей
     *
     * @return
     */
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    /**
     * Получение пользователя по ID
     *
     * @param userId
     * @return
     */
    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    /**
     * Создание пользователя
     *
     * @param user
     * @return
     */
    public User createUser(User user) {
        if (user == null) {
            throw new ValidationException("user is null");
        }
        return userStorage.createUser(user);
    }

    /**
     * Обновление информации о пользователе
     *
     * @param user
     * @return
     */
    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            throw new EntityNotFoundException("user not found");
        }
        return userStorage.updateUser(user);
    }

    /**
     * Удаление пользователя
     *
     * @param user
     */
    public void removeUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            throw new EntityNotFoundException("user not found");
        }
        userStorage.deleteUser(user);
    }

    /**
     * Добавление друга
     *
     * @param userId
     * @param friendId
     */
    public void addFriend(Long userId, Long friendId) {
        if (!userStorage.getUsers().contains(getUserById(userId)) ||
                !userStorage.getUsers().contains(getUserById(friendId))) {
            throw new EntityNotFoundException("user not found");
        }
        getUserById(userId).getFriends().add(getUserById(friendId));
        getUserById(friendId).getFriends().add(getUserById(userId));
    }

    /**
     * Удаление друга
     *
     * @param userId
     * @param friendId
     */
    public void removeFriend(Long userId, Long friendId) {
        if (!userStorage.getUsers().contains(getUserById(userId)) ||
                !userStorage.getUsers().contains(getUserById(friendId))) {
            throw new EntityNotFoundException("user not found");
        }
        getUserById(userId).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(userId);
    }

    /**
     * получить друзей пользователя
     *
     * @param userId
     * @return
     */
    public List<User> getListFriends(Long userId) {
        List<User> userFriendsList = new ArrayList<>();

        for (User user1 : userStorage.getUserById(userId).getFriends()) {
            userFriendsList.add(userStorage.getUserById(user1.getId()));
        }
        return userFriendsList;
    }

    /**
     * получить общих друзей между двумя пользователями
     *
     * @param user1Id
     * @param user2Id
     * @return
     */
    public List<User> getCommonFriends(Long user1Id, Long user2Id) {
        List<User> users1 = userStorage.getUserById(user1Id).getFriends();
        List<User> users2 = userStorage.getUserById(user2Id).getFriends();

        List<User> commonFriends = (List<User>) users1.stream().filter(users2::contains).collect(Collectors.toList()).
                stream().map(x -> getUserById(x.getId())).collect(Collectors.toList());

        return commonFriends;
    }


}