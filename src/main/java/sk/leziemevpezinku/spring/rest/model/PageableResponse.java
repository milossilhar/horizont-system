package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse<T> {

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("pageNumber")
    private long pageNumber;

    @JsonProperty("pageSize")
    private long pageSize;

    @JsonProperty("totalPages")
    private long totalPages;

    @JsonProperty("totalElements")
    private long totalElements;

    public static <T> PageableResponse<T> of(Page<T> page) {
        if (page.isEmpty()) return PageableResponse.<T>builder()
                .content(Collections.emptyList())
                .build();

        return PageableResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
