package kakao.beno2homework.v1.repository;

import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v1.dto.ScheduleRequestDto;
import kakao.beno2homework.v1.dto.ScheduleResponseDto;
import kakao.beno2homework.v1.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v1.entity.Schedule;
import kakao.beno2homework.v1.exception.BadRequestScheduleException;
import kakao.beno2homework.v1.exception.NotFoundScheduleException;
import kakao.beno2homework.v1.exception.ScheduleRequestFailException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleResponseDto save(ScheduleRequestDto dto) {

        String insertSQL = "insert into schedule (author, password, content) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getAuthor());
            ps.setString(2, dto.getPassword());
            ps.setString(3, dto.getContent());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        Schedule savedSchedule = findScheduleById(generatedId, "일정이 저장되지 않았습니다.");

        return new ScheduleResponseDto(savedSchedule);
    }


    public List<ScheduleResponseDto> findSchedules(String author, LocalDate updateDate) {
        StringBuilder sql = new StringBuilder("select * from schedule");
        List<Object> params = new ArrayList<>();

        if (author != null || updateDate != null) {
            sql.append(" where");

            if (author != null) {
                sql.append(" author = ?");
                params.add(author);
            }

            if (updateDate != null) {
                if (!params.isEmpty()) {
                    sql.append(" AND");
                }
                sql.append(" update_time >= ? and update_time < ?");
                params.add(updateDate.atStartOfDay());
                params.add(updateDate.plusDays(1).atStartOfDay());
            }
        }

        sql.append(" order by update_time desc");

        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleRowMapper()
                ).stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule findSchedule = findScheduleById(id, "존재하는 일정이 없습니다.");

        return new ScheduleResponseDto(findSchedule);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateReqDto dto) {
        List<Object> params = new ArrayList<>();

        Schedule findSchedule = findScheduleById(id, "존재하는 일정이 없습니다.");

        if (!findSchedule.getPassword().equals(dto.getPassword()))
            throw new BadRequestScheduleException("비밀번호가 일치하지 않습니다.");

        StringBuilder updateSQL = new StringBuilder("update schedule set ");

        if (dto.getAuthor() != null && dto.getContent() != null) {
            updateSQL.append("author = ?, content = ?");
            params.add(dto.getAuthor());
            params.add(dto.getContent());
        }
        else if (dto.getAuthor() != null) {
            updateSQL.append("author = ?");
            params.add(dto.getAuthor());
        }
        else if (dto.getContent() != null) {
            updateSQL.append("content = ?");
            params.add(dto.getContent());
        }
        else return new ScheduleResponseDto(findSchedule);


        updateSQL.append(" where schedule_id = ?");
        params.add(id);
        int updated = jdbcTemplate.update(updateSQL.toString(), params.toArray());

        if (updated  == 0) throw new ScheduleRequestFailException("수정 실패");

        Schedule updatedSchedule = findScheduleById(id, "존재하는 일정이 없습니다.");

        return new ScheduleResponseDto(updatedSchedule);
    }

    public void deleteSchedule(Long id, ScheduleDeleteReqDto dto) {

        Schedule findSchedule  = findScheduleById(id, "존재하는 일정이 없습니다.");

        if (!findSchedule.getPassword().equals(dto.getPassword()))
            throw new BadRequestScheduleException("비밀번호가 일치하지 않습니다.");

        String deleteSQL = "delete from schedule where schedule_id = ?";

        int deleted = jdbcTemplate.update(deleteSQL, id);

        if (deleted == 0) throw new ScheduleRequestFailException("삭제 실패");
    }

    private Schedule findScheduleById(Long generatedId, String message) {
        String selectSQL = "select * from schedule where schedule_id = ?";
        try {
            Schedule findSchedule = jdbcTemplate.queryForObject(selectSQL, scheduleRowMapper(), generatedId);
            return findSchedule;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundScheduleException(message);
        }
    }


    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("schedule_id"),
                rs.getString("author"),
                rs.getString("password"),
                rs.getString("content"),
                rs.getObject("create_time", LocalDateTime.class),
                rs.getObject("update_time", LocalDateTime.class)
        );
    }

}
