package kakao.beno2homework.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorResponse {

    private String field;
    private String message;
}
