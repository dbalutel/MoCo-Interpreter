package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "T_LESSON_INSTANCE")
public class LessonInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar2(50)")
    private String name;

    @Column(name = "number", columnDefinition = "integer")
    private Integer number;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ElementCollection
    @Column(name = "required_to_access")
    private List<Integer> requiredToAccess = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_course_instance", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference("lesson")
    private CourseInstance courseInstance;

    @OneToMany(mappedBy = "lessonInstance", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TestInstance> testInstances = new ArrayList<>();

    public void setTestInstances(List<TestInstance> testInstances) {
        this.testInstances.addAll(testInstances);
        testInstances.forEach(testInstance -> testInstance.setLessonInstance(this));
    }
}
