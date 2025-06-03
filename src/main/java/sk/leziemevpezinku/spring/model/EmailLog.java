package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_email_logs")
public class EmailLog extends AuditedCreationEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_email_logs_id")
    @SequenceGenerator(name = "seq_email_logs_id", sequenceName = "seq_email_logs_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "registration_id", nullable = false)
    private Long registrationId;

    @Column(name = "email_type", nullable = false, length = 50)
    private String emailType;

    @Column(name = "recipients", nullable = false, length = 50)
    private String recipients;

    @Column(name = "html", nullable = false, length = 25000)
    private String html;
}
