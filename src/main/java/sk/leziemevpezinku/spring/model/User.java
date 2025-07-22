package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_user")
public class User extends CreatedAtEntity {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    @SequenceGenerator(name = "seq_user_id", sequenceName = "seq_user_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "telPhone", length = 20, nullable = false)
    private String telPhone;

    @ToString.Include
    @Column(name = "verified")
    private Boolean verified;

    @ToString.Include
    @Column(name = "disabled")
    private Boolean disabled;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(name = "roles")
    private List<String> roles = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reg_user_known_person", joinColumns = @JoinColumn(name = "user_id"))
    private List<KnownPerson> knownPeople = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Person> people = new HashSet<>();
}
