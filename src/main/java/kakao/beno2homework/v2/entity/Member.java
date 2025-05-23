package kakao.beno2homework.v2.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
