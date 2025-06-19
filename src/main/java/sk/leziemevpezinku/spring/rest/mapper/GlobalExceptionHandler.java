package sk.leziemevpezinku.spring.rest.mapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import sk.leziemevpezinku.spring.rest.model.GenericError;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public GenericError accessDeniedExceptionHandler(AccessDeniedException ex) {
        var error = ErrorCode.MSG_ACCESS_DENIED;
        return GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage())
                .parameter("exceptionMessage", ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public GenericError noResourceFoundExceptionHandler(NoResourceFoundException ex) {
        var error = ErrorCode.MSG_NOT_FOUND;
        return GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage())
                .parameter("resourcePath", ex.getResourcePath())
                .build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GenericError> responseStatusExceptionHandler(ResponseStatusException ex) {
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
    public ResponseEntity<GenericError> commonExceptionHandler(CommonException ex) {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericError> defaultExceptionHandler(Exception ex) {
        var error = ErrorCode.MSG_GENERIC_ERROR;
        var uuid = UUID.randomUUID().toString();
        log.error("Generic error in rest call {}.", uuid, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericError.builder()
                        .code(error.name())
                        .statusCode(error.getStatus().value())
                        .message(error.getMessage())
                        .parameter("uuid", uuid)
                        .build()
                );
    }
}
