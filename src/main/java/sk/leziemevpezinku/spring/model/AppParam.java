package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_app_param")
public class AppParam {

    @Id
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "value", length = 200, nullable = false)
    private String value;

    public interface Names {
        String SCHEDULE_PAYMENT_INFO_ENABLE = "SCHEDULE_PAYMENT_INFO_ENABLE";
        String SCHEDULE_PAYMENT_INFO_BATCH_SIZE = "SCHEDULE_PAYMENT_INFO_BATCH_SIZE";
        String SCHEDULE_EVENT_DETAIL_ENABLE = "SCHEDULE_EVENT_DETAIL_ENABLE";
        String SCHEDULE_EVENT_DETAIL_BATCH_SIZE = "SCHEDULE_EVENT_DETAIL_BATCH_SIZE";
    }
}
