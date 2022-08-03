package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MPADbStorage implements  MPAStorage{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MPA> getAll() {
        String sqlString = "select * from ratings ";
        return jdbcTemplate.query(sqlString, (rs, row) -> MPAObj(rs));
    }

    @Override
    public MPA getMPA(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from ratings where rating_id = ?", id);
        if (userRows.next()) {
            return new MPA(id, userRows.getString("name"));
        }
        return null;
    }

    private MPA MPAObj(ResultSet rs) throws SQLException {
        return new MPA(
                rs.getLong("rating_id"),
                rs.getString("name"));
    }

}
