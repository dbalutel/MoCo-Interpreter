package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "name", columnDefinition = "varchar2(50)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_user_name", referencedColumnName = "name", nullable = false, updatable = false)
    @JsonManagedReference("user")
    private User user;

    @ElementCollection
    @Column(name = "visited_lessons")
    private List<Integer> visitedLessons = new ArrayList<>();

    @OneToMany(mappedBy = "courseInstance", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference("lesson")
    private List<LessonInstance> lessonInstances = new ArrayList<>();
}
