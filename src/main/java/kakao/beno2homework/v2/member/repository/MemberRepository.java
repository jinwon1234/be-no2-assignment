package kakao.beno2homework.v2.member.repository;

import kakao.beno2homework.common.memberEx.MemberRequestFailException;
import kakao.beno2homework.v2.entity.Member;
import kakao.beno2homework.v2.member.dto.MemberJoinDto;
import kakao.beno2homework.v2.member.dto.MemberResponseDto;
import kakao.beno2homework.common.memberEx.NotFoundMemberException;
import kakao.beno2homework.v2.member.dto.MemberUpdatePasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "v2.member";

    public MemberResponseDto save(MemberJoinDto dto) {

        String insertSQL = "insert into "+ TABLE_NAME +  " (email, name, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getPassword());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        Member member = findById(generatedId).orElseThrow(() -> new NotFoundMemberException("회원가입 실패"));

        return new MemberResponseDto(member);
    }

    public Optional<Member> findByEmail(String email) {
        String selectSQL = "select * from " + TABLE_NAME + " where email = ?";
        try {
            Member findMember = jdbcTemplate.queryForObject(selectSQL, memberRowMapper(), email);
            return Optional.of(findMember);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updatePassword(String password, Long id) {

        String updateSQL = "update " + TABLE_NAME + " set password = ? where member_id = ?";

        int update = jdbcTemplate.update(updateSQL, password, id);

        if (update == 0) throw new MemberRequestFailException("비밀번호 변경 실패");
    }

    public void delete(Long id) {
        String deleteSQL = "delete from " + TABLE_NAME + " where member_id = ?";

        int delete = jdbcTemplate.update(deleteSQL, id);

        if (delete == 0) throw new MemberRequestFailException("회원 탈퇴 실패");
    }
    public void updateName(String name, Long id) {
        String updateSQL = "update " + TABLE_NAME + " set name = ? where member_id = ?";

        int update = jdbcTemplate.update(updateSQL, name, id);

        if (update == 0) throw new MemberRequestFailException("이름 변경 실패");
    }

    public List<Member> findByName(String name) {
        String selectSQL = "select * from " + TABLE_NAME + " where name = ?";
        return jdbcTemplate.query(selectSQL, memberRowMapper(), name);
    }

    public Optional<Member> findById(Long generatedId) {
        String selectSQL = "select * from " + TABLE_NAME + " where member_id = ?";
        try {
            Member findMember = jdbcTemplate.queryForObject(selectSQL, memberRowMapper(), generatedId);
            return Optional.of(findMember);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("member_id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getObject("create_time", LocalDateTime.class),
                rs.getObject("update_time", LocalDateTime.class)
        );
    }

}
