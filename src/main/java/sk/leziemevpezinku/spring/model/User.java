package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntityBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_user")
public class User extends CreatedAtEntityBase {

    @Id
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

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "disabled")
    private Boolean disabled;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(name = "roles")
    private List<String> roles = new ArrayList<>();

    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reg_user_known_person", joinColumns = @JoinColumn(name = "user_id"))
    private List<KnownPerson> knownPeople = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Person> people = new HashSet<>();
}
