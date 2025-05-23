package kakao.beno2homework.v2.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberJoinDto {


    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;

    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 필수로 입력해주셔야합니다.")
    private String name;
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 필수로 입력해주셔야합니다.")
    private String password;


}
