package kakao.beno2homework.v2.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberUpdateNameDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 필수로 입력해주셔야합니다.")
    private String updateName;
}
