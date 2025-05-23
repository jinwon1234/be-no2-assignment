package kakao.beno2homework.v1.repository;

import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v1.dto.ScheduleRequestDto;
import kakao.beno2homework.v1.dto.ScheduleResponseDto;
import kakao.beno2homework.v1.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v1.entity.Schedule;
import kakao.beno2homework.common.scheduleEx.BadRequestScheduleException;
import kakao.beno2homework.common.scheduleEx.NotFoundScheduleException;
import kakao.beno2homework.common.scheduleEx.ScheduleRequestFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryV1 {

    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "v1.schedule";

    public ScheduleResponseDto save(ScheduleRequestDto dto) {

        String insertSQL = "insert into "+ TABLE_NAME +  " (author, password, content) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getAuthor());
            ps.setString(2, dto.getPassword());
            ps.setString(3, dto.getContent());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() == null) throw new ScheduleRequestFailException("일정이 저장되지 않았습니다.");

        Long generatedId = keyHolder.getKey().longValue();

        Schedule savedSchedule  = findById(generatedId)
                .orElseThrow(()-> new NotFoundScheduleException("존재하는 일정이 없습니다."));

        return new ScheduleResponseDto(savedSchedule);
    }


    public List<ScheduleResponseDto> findSchedules(String author, LocalDate updateDate) {
        StringBuilder sql = new StringBuilder("select * from " + TABLE_NAME);
        List<Object> params = new ArrayList<>();
        List<String> whereClauses = new ArrayList<>();


        if (author != null) {
            whereClauses.add(" author = ?");
            params.add(author);
        }
        if (updateDate != null) {
            whereClauses.add(" update_time >= ? and update_time < ?");
            params.add(updateDate.atStartOfDay());
            params.add(updateDate.plusDays(1).atStartOfDay());
        }

        if (!whereClauses.isEmpty()) {
            sql.append(" where ").append(String.join(" and ", whereClauses));
        }

        sql.append(" order by update_time desc");

        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleRowMapper()
                ).stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateReqDto dto) {
        Schedule findSchedule = findById(id)
                .orElseThrow(() -> new NotFoundScheduleException("존재하지 않는 일정입니다."));

        if (!findSchedule.getPassword().equals(dto.getPassword())) {
            throw new BadRequestScheduleException("비밀번호가 일치하지 않습니다.");
        }

        StringBuilder updateSQL = new StringBuilder("update " + TABLE_NAME + " set ");
        List<String> setClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (dto.getAuthor() != null) {
            setClauses.add("author = ?");
            params.add(dto.getAuthor());
        }

        if (dto.getContent() != null) {
            setClauses.add("content = ?");
            params.add(dto.getContent());
        }

        if (setClauses.isEmpty()) {
            return new ScheduleResponseDto(findSchedule);
        }

        updateSQL.append(String.join(", ", setClauses));
        updateSQL.append(" where schedule_id = ?");
        params.add(id);

        int updated = jdbcTemplate.update(updateSQL.toString(), params.toArray());
        if (updated == 0) {
            throw new ScheduleRequestFailException("수정 실패");
        }

        Schedule updatedSchedule = findById(id)
                .orElseThrow(() -> new NotFoundScheduleException("존재하지 않는 일정입니다."));

        return new ScheduleResponseDto(updatedSchedule);
    }


    public void deleteSchedule(Long id, ScheduleDeleteReqDto dto) {

        Schedule findSchedule  = findById(id)
                .orElseThrow(()-> new NotFoundScheduleException("존재하지 않는 일정입니다."));

        if (!findSchedule.getPassword().equals(dto.getPassword()))
            throw new BadRequestScheduleException("비밀번호가 일치하지 않습니다.");

        String deleteSQL = "delete from " + TABLE_NAME + " where schedule_id = ?";

        int deleted = jdbcTemplate.update(deleteSQL, id);

        if (deleted == 0) throw new ScheduleRequestFailException("삭제 실패");
    }

    public Optional<Schedule> findById(Long generatedId) {
        String selectSQL = "select * from " + TABLE_NAME + " where schedule_id = ?";
        try {
            Schedule findSchedule = jdbcTemplate.queryForObject(selectSQL, scheduleRowMapper(), generatedId);
            return Optional.of(findSchedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
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
