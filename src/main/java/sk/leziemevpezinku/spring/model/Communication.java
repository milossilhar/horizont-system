package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.api.enumeration.CommunicationChannel;
import sk.leziemevpezinku.spring.api.enumeration.CommunicationReference;
import sk.leziemevpezinku.spring.api.enumeration.CommunicationStatus;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntity;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_communication")
public class Communication extends CreatedAtEntity {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_communication_id")
    @SequenceGenerator(name = "seq_communication_id", sequenceName = "seq_communication_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 10)
    private CommunicationChannel channel;

    @Builder.Default
    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CommunicationStatus status = CommunicationStatus.PENDING;

    @ToString.Include
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @ToString.Include
    @Column(name = "recipient_phone", length = 20)
    private String recipientPhone;

    @ToString.Include
    @Column(name = "recipient_email", length = 100)
    private String recipientEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference", length = 20)
    private CommunicationReference reference;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "message", length = 2000)
    private String message;
}
