package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUserDaoImpl implements UserDao {

    private static final String TABLE_NAME = "user";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<UserDto> userRowMapper = new RowMapper<UserDto>() {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Date date = rs.getDate("user_birth_date");
            UserDto user = new UserDto(
                    rs.getLong("user_idx"),
                    rs.getString("user_email"),
                    rs.getString("user_pw"),
                    rs.getString("user_nickname"),
                    rs.getString("user_gender"),
                    date == null ? null : date.toLocalDate(),
                    rs.getString("user_job"),
                    rs.getBoolean("user_service_agree"),
                    rs.getBoolean("user_privacy_agree"),
                    rs.getBoolean("user_location_agree"),
                    rs.getBoolean("user_promotion_agree")
            );
            return user;
        }
    };

    @Override
    public List<UserDto> selectAll() {
        StringBuilder sql = new StringBuilder();
        sql
                .append("select * from").append(" ")
                .append(TABLE_NAME);

        List<UserDto> userList = jdbcTemplate.query(sql.toString(), userRowMapper);

        return userList.isEmpty() ? null : userList;
    }

    @Override
    public void deleteAll() {
        StringBuilder sql = new StringBuilder();
        sql
                .append("delete from").append(" ")
                .append(TABLE_NAME);

        jdbcTemplate.update(sql.toString());
    }
}
