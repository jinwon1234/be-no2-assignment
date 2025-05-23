package kakao.beno2homework.v2.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Size(max = 200, message = "내용(할 일)은 200자 이하만 입력가능합니다.")
    @NotBlank(message = "내용(할 일) 입력은 필수입니다.")
    private String content;
}
