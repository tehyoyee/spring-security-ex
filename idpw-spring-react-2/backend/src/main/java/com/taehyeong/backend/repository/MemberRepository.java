package com.taehyeong.backend.repository;

import com.taehyeong.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.PreparedStatement;
import java.util.Optional;
//


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

}
//
//@Repository
//public class MemberRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public MemberRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public Optional<Member> findByUsername(String username) {
//        String sql = "SELECT id, username, password FROM member WHERE username = ?";
//
//        try {
//            Member member = jdbcTemplate.queryForObject(
//                    sql,
//                    new Object[]{username},
//                    (rs, rowNum) -> {
//                        Member m = new Member();
//                        m.setId(rs.getLong("id"));
//                        m.setUsername(rs.getString("username"));
//                        m.setPassword(rs.getString("password"));
//                        return m;
//                    }
//            );
//            return Optional.ofNullable(member);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Member save(Member member) {
//        String sql = "INSERT INTO member (username, password) VALUES (?, ?)";
//
//        // KeyHolder를 사용하여 생성된 ID를 가져옴
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
//            ps.setString(1, member.getUsername());
//            ps.setString(2, member.getPassword());
//            return ps;
//        }, keyHolder);
//
//        // 생성된 ID를 Member 객체에 설정
//        Number key = keyHolder.getKey();
//        if (key != null) {
//            member.setId(key.longValue());
//        }
//        return member;
//    }
//
//    // Password 업데이트 메서드
//    public int updatePassword(Long memberId, String newPassword) {
//        String sql = "UPDATE member SET password = ? WHERE id = ?";
//        return jdbcTemplate.update(sql, newPassword, memberId);
//    }
//
//    public int deleteById(Long id) {
//        String sql = "DELETE FROM members WHERE id = ?";
//        return jdbcTemplate.update(sql, id);
//    }
//}
