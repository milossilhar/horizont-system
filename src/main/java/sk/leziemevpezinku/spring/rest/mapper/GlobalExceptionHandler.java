package sk.leziemevpezinku.spring.rest.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import sk.leziemevpezinku.spring.rest.model.GenericError;
import sk.leziemevpezinku.spring.service.exception.CommonException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GenericError> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .headers(ex.getHeaders())
                .body(GenericError.builder()
                        .code(ex.getStatusCode().toString())
                        .statusCode(ex.getStatusCode().value())
                        .message(ex.getReason())
                        .build()
                );
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<GenericError> handleCommonException(CommonException ex) {
        return ResponseEntity
                .status(ex.getErrorCode().getStatus().value())
                .body(GenericError.builder()
                        .code(ex.getErrorCode().name())
                        .statusCode(ex.getErrorCode().getStatus().value())
                        .message(ex.getErrorCode().getMessage())
                        .parameters(ex.getParameters())
                        .build()
                );
    }
}
