package kakao.beno2homework.v1.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ScheduleUpdateReqDto {
    private String author;
    private String content;
    private String password;
}
