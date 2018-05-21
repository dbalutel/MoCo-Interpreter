package md.balutsel.mocointerpreter.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_COURSE_INSTANCE")
public class CourseInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_user_name", referencedColumnName = "name", nullable = false, updatable = false)
    private User user;

    @ElementCollection
    @Column(name = "visited_lessons")
    private List<Integer> visitedLessons = new ArrayList<>();

    @OneToMany(mappedBy = "courseInstance", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TestInstance> testInstances = new ArrayList<>();

    public void addTestInstance(TestInstance testInstance) {
        testInstance.setCourseInstance(this);
        testInstances.add(testInstance);
    }
}
