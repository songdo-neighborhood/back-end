package neighborhood.songdo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    public ResponseEntity<ErrorDto> handleCustomException(CustomException exception) {
        log.error("CustomException: {}", exception.getMessage(), exception);
        return new ErrorDto(exception.getErrorCode()).toResponseEntity();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String errorMessage = errors.toString();
        log.warn("Validation failed: {}", errorMessage);
        return new ErrorDto(ErrorCode.INVALID_PARAMETER).toResponseEntity();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorDto> handleServerException(Exception exception) {
        log.error("Unexpected error occurred", exception);
        return new ErrorDto(ErrorCode.INTERNAL_SERVER_ERROR).toResponseEntity();
    }
}
