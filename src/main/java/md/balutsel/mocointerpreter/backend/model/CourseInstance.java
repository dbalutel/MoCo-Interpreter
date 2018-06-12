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

    @Column(name = "username", columnDefinition = "varchar2(255)", updatable = false, nullable = false)
    private String username;

    @ElementCollection
    @Column(name = "visited_lessons")
    private List<Integer> visitedLessons = new ArrayList<>();

    @OneToMany(mappedBy = "courseInstance", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference("lesson")
    private List<LessonInstance> lessonInstances = new ArrayList<>();

    public void addVisitedLesson(Integer lessonNumber) {
        visitedLessons.add(lessonNumber);
    }

    public void setLessonInstances(List<LessonInstance> lessonInstances) {
        this.lessonInstances.addAll(lessonInstances);
        lessonInstances
                .forEach(lessonInstance -> lessonInstance.setCourseInstance(this));
    }
}
