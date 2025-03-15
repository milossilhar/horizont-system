package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
    @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_surname")
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_person_substitute_lesson", joinColumns = @JoinColumn(name = "person_id"))
    private List<SubstituteLesson> substituteLessons;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attendance")
    private Set<Lesson> attendedLessons;
}
