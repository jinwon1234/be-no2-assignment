package kakao.beno2homework.v1.dto;

import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleRequestDto {

    private String author;
    private String password;
    private String content;
}
