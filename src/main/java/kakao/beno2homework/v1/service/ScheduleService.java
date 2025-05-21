package kakao.beno2homework.v1.service;

import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v1.dto.ScheduleRequestDto;
import kakao.beno2homework.v1.dto.ScheduleResponseDto;
import kakao.beno2homework.v1.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v1.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto saved = scheduleRepository.save(scheduleRequestDto);

        return saved;
    }

    public List<ScheduleResponseDto> getSchedules(String author, LocalDate updateDate) {
        return scheduleRepository.findSchedules(author, updateDate);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateReqDto scheduleUpdateReqDto) {
        return scheduleRepository.updateSchedule(id, scheduleUpdateReqDto);
    }

    public void deleteSchedule(Long id, ScheduleDeleteReqDto scheduleDeleteReqDto) {
        scheduleRepository.deleteSchedule(id, scheduleDeleteReqDto);
    }


}
