package kakao.beno2homework.v2.member.dto;

import kakao.beno2homework.v2.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
