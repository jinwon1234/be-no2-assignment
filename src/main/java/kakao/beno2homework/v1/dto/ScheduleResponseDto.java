package kakao.beno2homework.v1.dto;

import kakao.beno2homework.v1.entity.Schedule;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ScheduleResponseDto {

    private Long id;
    private String author;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.author = schedule.getAuthor();
        this.content = schedule.getContent();
        this.createTime = schedule.getCreateTime();
        this.updateTime = schedule.getUpdateTime();
    }
}
