package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
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
                    rs.getBoolean("user_promotion_agree"),
                    rs.getString("user_role")
            );
            return user;
        }
    };

    @Override
    public UserDto selectUserByUserEmail(String userEmail) {
        StringBuilder sql = new StringBuilder();
        sql
                .append("SELECT * FROM").append(" ")
                .append(TABLE_NAME).append(" ")
                .append("WHERE user_email = ?");

        return jdbcTemplate.query(sql.toString(), userRowMapper, userEmail).get(0);
    }

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

    @Override
    public void insertUser(UserDto user) {
        StringBuilder sql = new StringBuilder();
        sql
                .append("INSERT INTO").append(" ")
                .append(TABLE_NAME).append(" ")
                .append("(" +
                        "user_email, user_pw, user_nickname, " +
                        "user_gender, user_birth_date, user_job, " +
                        "user_service_agree, user_privacy_agree, user_location_agree, user_promotion_agree, user_role)").append(" ")
                .append("VALUES").append(" ")
                .append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[] {"user_idx"});
            pstmt.setString(1, user.getUserEmail());
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getUserNickname());
            pstmt.setString(4, user.getUserGender());
            pstmt.setDate(5, Date.valueOf(user.getUserBirthDate()));
            pstmt.setString(6, user.getUserJob());
            pstmt.setBoolean(7, user.isUserServiceAgree());
            pstmt.setBoolean(8, user.isUserPrivacyAgree());
            pstmt.setBoolean(9, user.isUserLocationAgree());
            pstmt.setBoolean(10, user.isUserPromotionAgree());
            pstmt.setString(11, user.getRole());
            return pstmt;
        }, keyHolder);

        user.setUserIdx(keyHolder.getKey().longValue());
    }

    @Override
    public int selectCountByEmail(String userEmail) {
        StringBuilder sql = new StringBuilder();
        sql
                .append("SELECT COUNT(*) FROM").append(" ")
                .append(TABLE_NAME).append(" ")
                .append("WHERE user_email = ?");

        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, userEmail);
    }

    @Override
    public int selectCountByNickname(String userNickname) {
        StringBuilder sql = new StringBuilder();
        sql
                .append("SELECT COUNT(*) FROM").append(" ")
                .append(TABLE_NAME).append(" ")
                .append("WHERE user_nickname = ?");

        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, userNickname);
    }
}
