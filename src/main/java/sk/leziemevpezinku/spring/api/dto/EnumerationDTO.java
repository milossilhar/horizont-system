package sk.leziemevpezinku.spring.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationDTO {

    private boolean administrated;

    private List<? extends EnumerationItemDTO> values;
}
