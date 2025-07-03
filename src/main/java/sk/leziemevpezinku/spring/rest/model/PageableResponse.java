package sk.leziemevpezinku.spring.rest.model;

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
    private List<T> content;
    private long pageNumber;
    private long pageSize;
    private long totalPages;
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
