package kakao.beno2homework.common.memberEx;

import kakao.beno2homework.common.FieldErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorResponse>> handleValidationException(MethodArgumentNotValidException e) {

        List<FieldErrorResponse> response = e.getBindingResult().getFieldErrors()
                .stream().map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(BadRequestMemberException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestMemberException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationFailedException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateException(DuplicateMemberException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundMemberException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(MemberRequestFailException.class)
    public ResponseEntity<Map<String, String>> handleMemberRequestFailException(MemberRequestFailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
    }
}
