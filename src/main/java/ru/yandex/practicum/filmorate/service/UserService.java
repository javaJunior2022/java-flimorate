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
        getUserById(userId).getFriends().add(friendId);
        getUserById(friendId).getFriends().add(userId);
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

        for (Long id1 : userStorage.getUserById(userId).getFriends()) {
            userFriendsList.add(userStorage.getUserById(id1));
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
        Set<Long> users1 = userStorage.getUserById(user1Id).getFriends();
        Set<Long> users2 = userStorage.getUserById(user2Id).getFriends();
        List<User> commonFriends = new ArrayList<>();

        for (Long id1 : users1) {
            for (Long id2 : users2) {
                if (id1.equals(id2)) {
                    commonFriends.add(userStorage.getUserById(id1));
                }
            }
        }
        return commonFriends;
    }


}