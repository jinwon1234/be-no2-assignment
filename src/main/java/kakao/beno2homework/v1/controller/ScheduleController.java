package kakao.beno2homework.v1.controller;

import kakao.beno2homework.v1.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v1.dto.ScheduleRequestDto;
import kakao.beno2homework.v1.dto.ScheduleResponseDto;
import kakao.beno2homework.v1.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v1.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        ScheduleResponseDto response = scheduleService.saveSchedule(scheduleRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(@RequestParam(required = false) String author,
                                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updateDate) {
        List<ScheduleResponseDto> response = scheduleService.getSchedules(author, updateDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto response = scheduleService.getSchedule(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateReqDto scheduleUpdateReqDto) {
        ScheduleResponseDto response = scheduleService.updateSchedule(scheduleId, scheduleUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDeleteReqDto scheduleDeleteReqDto) {
        scheduleService.deleteSchedule(scheduleId, scheduleDeleteReqDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
