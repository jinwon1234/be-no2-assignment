package kakao.beno2homework.v1.service;

import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v1.dto.ScheduleRequestDto;
import kakao.beno2homework.v1.dto.ScheduleResponseDto;
import kakao.beno2homework.v1.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v1.entity.Schedule;
import kakao.beno2homework.common.scheduleEx.NotFoundScheduleException;
import kakao.beno2homework.v1.repository.ScheduleRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceV1 {

    private final ScheduleRepositoryV1 scheduleRepository;

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto saved = scheduleRepository.save(scheduleRequestDto);

        return saved;
    }

    public List<ScheduleResponseDto> getSchedules(String author, LocalDate updateDate) {
        return scheduleRepository.findSchedules(author, updateDate);
    }

    public ScheduleResponseDto getSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundScheduleException("존재하는 일정이 없습니다."));

        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateReqDto scheduleUpdateReqDto) {
        return scheduleRepository.updateSchedule(id, scheduleUpdateReqDto);
    }

    public void deleteSchedule(Long id, ScheduleDeleteReqDto scheduleDeleteReqDto) {
        scheduleRepository.deleteSchedule(id, scheduleDeleteReqDto);
    }


}
