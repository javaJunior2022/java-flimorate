package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDBStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        String sqlString = "select * from genres";
        return jdbcTemplate.query(sqlString, (rs, row) -> genreObj(rs));
    }

    private Genre genreObj(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getLong("genre_id"),
                rs.getString("name"));
    }

    @Override
    public Genre getGenre(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from genres where genre_id = ?", id);
        if (userRows.next()) {
            return new Genre(id, userRows.getString("name"));
        }
        return null;
    }

    @Override
    public void createGenre(Long film_id, Long genre_id) {
        String sqlString = "insert into films_genres (film_id,genre_id) values (?,?)";
        jdbcTemplate.update(sqlString, film_id, genre_id);
    }

    @Override
    public void removeGenre(Long id) {
        String sqlString = "delete into films_genres where film_id=?";
        jdbcTemplate.update(sqlString, id);
    }
}
