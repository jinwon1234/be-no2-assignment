package kakao.beno2homework.v2.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdatePasswordDto {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 필수로 입력해주셔야합니다.")
    private String changePassword;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 필수로 입력해주셔야합니다.")
    private String confirmPassword;
}
