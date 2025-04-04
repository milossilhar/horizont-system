package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_user")
public class User extends AuditedCreationEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    @SequenceGenerator(name = "seq_user_id", sequenceName = "seq_user_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "tel_phone", length = 20)
    private String telPhone;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Person> people = new ArrayList<>();

    @OrderColumn(name = "ind")
    @JsonProperty("known_people")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_user_known_person", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserKnownPerson> knownPeople = new ArrayList<>();
}
