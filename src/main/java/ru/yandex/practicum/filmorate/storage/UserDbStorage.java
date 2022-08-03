package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private long id = 0;

    @Override
    public User createUser(User user) {

        if (getUsers().contains(user)) {
            log.debug("User exists:", user);
            throw new ValidationException("User exists");
        }

        String sqlString = "insert into users (name, login, email, birthday) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sqlString,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                Date.valueOf(user.getBirthday()));
        user.setId(++id);

        log.debug("New user has been created:", user);
        return user;
    }

    @Override
    public User updateUser(User user) {


        return null;
    }

    @Override
    public void deleteUser(User user) {
        if (!getUsers().contains(user)) {
            log.debug("User does not exist:", user);
            throw new ValidationException("User does not exist");
        }

        String sqlString = "delete from users where user_id = ?";
        jdbcTemplate.update(sqlString, user.getId());
        log.debug("New user has been created:", user);
    }

    @Override
    public List<User> getUsers() {
        String sqlString = "select * from users";
        return jdbcTemplate.query(sqlString, (rs, rowNum) -> userObj(rs));
    }

    @Override
    public User getUserById(Long id) {

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);

        if (sqlRowSet.next()) {
            User user = new User(
                    sqlRowSet.getLong("user_id"),
                    sqlRowSet.getString("name"),
                    sqlRowSet.getString("login"),
                    sqlRowSet.getString("email"),
                    sqlRowSet.getDate("birthday").toLocalDate(),
                    getFriendsByUserId(id));
            log.info("user found", user.getName());
            return user;
        } else {
            log.info("user not found", id);
            return null;
        }
    }


    private User userObj(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("user_id"),
                rs.getString("name"),
                rs.getString("login"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate(),
                getFriendsByUserId(rs.getLong("user_id")));
    }

    private List<User> getFriendsByUserId(Long userId) {
        String sql = "select to_user_id from friends where 'isAccepted'=? and from_user_id = ?";
        Boolean t = true;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), t, userId);
    }

    private void usersValidation(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
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
    }
}
