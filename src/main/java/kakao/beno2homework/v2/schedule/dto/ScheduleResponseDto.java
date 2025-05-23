package kakao.beno2homework.v2.schedule.dto;

import kakao.beno2homework.v2.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String author;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public ScheduleResponseDto(Schedule schedule, String author) {
        this.id = schedule.getId();
        this.author = author;
        this.content = schedule.getContent();
        this.createTime = schedule.getCreateTime();
        this.updateTime = schedule.getUpdateTime();
    }
}
