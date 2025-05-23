package kakao.beno2homework.v2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String content;
    private Long memberId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}