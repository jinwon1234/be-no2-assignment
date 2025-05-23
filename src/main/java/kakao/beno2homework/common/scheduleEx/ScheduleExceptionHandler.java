package kakao.beno2homework.common.scheduleEx;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ScheduleExceptionHandler {

    @ExceptionHandler(NotFoundScheduleException.class)
    public ResponseEntity<Map<String,String>> notFoundHandler(NotFoundScheduleException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestScheduleException.class)
    public ResponseEntity<Map<String,String>> badRequestHandler(BadRequestScheduleException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScheduleRequestFailException.class)
    public ResponseEntity<Map<String,String>> scheduleRequestFailHandler(ScheduleRequestFailException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
