package kakao.beno2homework.v2.schedule.repository;

import kakao.beno2homework.common.scheduleEx.BadRequestScheduleException;
import kakao.beno2homework.common.scheduleEx.NotFoundScheduleException;
import kakao.beno2homework.common.scheduleEx.ScheduleRequestFailException;
import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v2.entity.Member;
import kakao.beno2homework.v2.entity.Schedule;
import kakao.beno2homework.v2.schedule.dto.ScheduleRequestDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleResponseDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleUpdateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryV2 {

    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "v2.schedule";

    public Schedule save(ScheduleRequestDto dto, Member member) {

        String insertSQL = "insert into "+ TABLE_NAME +  " (content, member_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getContent());
            ps.setLong(2 ,member.getId());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() == null) throw new ScheduleRequestFailException("일정이 저장되지 않았습니다.");

        Long generatedId = keyHolder.getKey().longValue();

        Schedule savedSchedule  = findById(generatedId)
                .orElseThrow(()-> new NotFoundScheduleException("존재하는 일정이 없습니다."));

        return savedSchedule;
    }

    public Optional<Schedule> findById(Long id) {
        String selectSQL = "select * from " + TABLE_NAME + " where schedule_id = ?";
        try {
            Schedule findSchedule = jdbcTemplate.queryForObject(selectSQL, scheduleRowMapper(), id);
            return Optional.of(findSchedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<ScheduleResponseDto> findByIdWithMemberName(Long id) {
        String selectSQL = "select s.*, m.name as member_name from " + TABLE_NAME + " s join v2.member m on m.member_id = s.member_id where s.schedule_id = ?";

        try {
            ScheduleResponseDto scheduleResponseDto = jdbcTemplate.queryForObject(selectSQL, scheduleRepDtoRowMapper(), id);
            return Optional.of(scheduleResponseDto);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Schedule> findByMemberId(Long memberId) {
        String selectSQL = "select * from " + TABLE_NAME + " where member_id = ?";
        return jdbcTemplate.query(selectSQL, scheduleRowMapper(), memberId);
    }

    public List<ScheduleResponseDto> findSchedules(List<Long> memberIdList, LocalDate updateDate, Pageable pageable) {
        StringBuilder sql = new StringBuilder(
                "select s.*, m.name as member_name from " + TABLE_NAME + " s " +
                        "join v2.member m on s.member_id = m.member_id"
        );
        List<Object> params = new ArrayList<>();
        List<String> whereClauses = new ArrayList<>();

        if (memberIdList != null && !memberIdList.isEmpty()) {
            String placeholders = memberIdList.stream().map(id -> "?").collect(Collectors.joining(", "));
            whereClauses.add("s.member_id in (" + placeholders + ")");
            params.addAll(memberIdList);
        }

        if (updateDate != null) {
            whereClauses.add("s.update_time >= ? and s.update_time < ?");
            params.add(updateDate.atStartOfDay());
            params.add(updateDate.plusDays(1).atStartOfDay());
        }

        if (!whereClauses.isEmpty()) {
            sql.append(" where ").append(String.join(" and ", whereClauses));
        }

        sql.append(" order by s.update_time desc");
        sql.append(" limit ? offset ?");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleRepDtoRowMapper());
    }

    public Schedule updateSchedule(Long id, ScheduleUpdateReqDto dto) {

        String updateSQL = "update " + TABLE_NAME + " set content = ? where schedule_id = ?";

        int updated = jdbcTemplate.update(updateSQL, dto.getContent(), id);

        if (updated  == 0) throw new ScheduleRequestFailException("수정 실패");

        Schedule updatedSchedule = findById(id)
                .orElseThrow(()-> new NotFoundScheduleException("존재하지 않는 일정입니다."));

        return updatedSchedule;
    }

    public void delete(Long id) {

        String deleteSQL = "delete from " + TABLE_NAME + " where schedule_id = ?";

        int deleted = jdbcTemplate.update(deleteSQL, id);

        if (deleted == 0) throw new ScheduleRequestFailException("삭제 실패");
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("schedule_id"),
                rs.getString("content"),
                rs.getLong("member_id"),
                rs.getObject("create_time", LocalDateTime.class),
                rs.getObject("update_time", LocalDateTime.class)
        );
    }

    private RowMapper<ScheduleResponseDto> scheduleRepDtoRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("schedule_id"),
                rs.getString("member_name"),
                rs.getString("content"),
                rs.getObject("create_time", LocalDateTime.class),
                rs.getObject("update_time", LocalDateTime.class)
        );
    }
}
