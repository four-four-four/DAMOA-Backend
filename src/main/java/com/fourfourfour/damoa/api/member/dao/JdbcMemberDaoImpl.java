package com.fourfourfour.damoa.api.member.dao;

import com.fourfourfour.damoa.api.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JdbcMemberDaoImpl implements MemberDao {

    private static final String TABLE_NAME = "tb_members";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<MemberDto> userRowMapper = new RowMapper<MemberDto>() {
        @Override
        public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Date date = rs.getDate("birth_date");
            MemberDto memberDto = new MemberDto(
                    rs.getLong("member_id"),
                    rs.getString("email"),
                    rs.getString("pw"),
                    rs.getString("nickname"),
                    rs.getString("gender"),
                    date == null ? null : date.toLocalDate(),
                    rs.getString("job"),
                    rs.getBoolean("service_agree"),
                    rs.getBoolean("privacy_agree"),
                    rs.getString("user_role")
            );
            return memberDto;
        }
    };

    @Override
    public MemberDto selectUserByUserEmail(String userEmail) {
        StringBuilder sql = new StringBuilder();
        sql
                .append("SELECT * FROM").append(" ")
                .append(TABLE_NAME).append(" ")
                .append("WHERE user_email = ?");

        return jdbcTemplate.query(sql.toString(), userRowMapper, userEmail).get(0);
    }

    @Override
    public List<MemberDto> selectAll() {
        StringBuilder sql = new StringBuilder();
        sql
                .append("select * from").append(" ")
                .append(TABLE_NAME);

        List<MemberDto> userList = jdbcTemplate.query(sql.toString(), userRowMapper);

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
    public void insertUser(MemberDto user) {
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
            PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[] {"id"});
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPw());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, Date.valueOf(user.getBirthDate()));
            pstmt.setString(6, user.getJob());
            pstmt.setBoolean(7, user.isServiceTerm());
            pstmt.setBoolean(8, user.isPrivacyTerm());
            pstmt.setString(11, user.getRole());
            return pstmt;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
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
