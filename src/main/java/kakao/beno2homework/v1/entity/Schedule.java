package kakao.beno2homework.v1.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String author;
    private String password;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Schedule(String author, String password, String content, LocalDateTime createTime, LocalDateTime updateTime) {
        this.author = author;
        this.password = password;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
