package kakao.beno2homework.v2.schedule.controller;

import jakarta.validation.Valid;

import kakao.beno2homework.v2.schedule.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleRequestDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleResponseDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v2.schedule.service.ScheduleServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ScheduleControllerV2 {

    private final ScheduleServiceV2 scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto response = scheduleService.save(scheduleRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getMemberSchedules(@PathVariable Long userId) {
        List<ScheduleResponseDto> response = scheduleService.findMemberSchedules(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto response = scheduleService.findSchedule(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules
            (@RequestParam(required = false) String author,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updateDate,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        List<ScheduleResponseDto> response = scheduleService.findSchedulesWithPage(author, updateDate, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleUpdateReqDto scheduleUpdateReqDto) {
        ScheduleResponseDto response = scheduleService.updateSchedule(scheduleId, scheduleUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleDeleteReqDto scheduleDeleteReqDto) {
        scheduleService.deleteSchedule(scheduleId, scheduleDeleteReqDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
