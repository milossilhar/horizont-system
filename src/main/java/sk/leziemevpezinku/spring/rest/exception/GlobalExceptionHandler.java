package sk.leziemevpezinku.spring.rest.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
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

import java.util.UUID;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericError methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        var error = ErrorCode.MSG_REQUEST_INVALID;
        final var builder = GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage());

        ex.getBindingResult().getAllErrors().forEach((objectError) -> {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            builder.parameter(fieldName, errorMessage);
        });

        return builder.build();
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
        log.warn("Resource {} not found in rest call.", ex.getMessage());
        var error = ErrorCode.MSG_NOT_FOUND;
        return GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage())
                .parameter("resourcePath", ex.getResourcePath())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public GenericError dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        log.error("Data integrity violation in rest call.", ex);
        var error = ErrorCode.MSG_DATA_INTEGRITY_VIOLATION;
        return GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public GenericError typeMismatchExceptionHandler(TypeMismatchException ex) {
        var error = ErrorCode.MSG_TYPE_MISMATCH;
        return GenericError.builder()
                .code(error.name())
                .statusCode(error.getStatus().value())
                .message(error.getMessage())
                .parameter("property", ex.getPropertyName())
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
