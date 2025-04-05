package sk.leziemevpezinku.spring.service.exception;

import lombok.*;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    private Map<String, String> parameters = new HashMap<>();
}
