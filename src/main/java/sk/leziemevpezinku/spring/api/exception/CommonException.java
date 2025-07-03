package sk.leziemevpezinku.spring.api.exception;

import lombok.*;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

import java.util.Map;

@Data
@Builder
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    @Singular
    private Map<String, Object> parameters;
}
