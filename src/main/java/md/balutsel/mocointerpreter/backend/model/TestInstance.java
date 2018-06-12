package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import md.balutsel.mocointerpreter.engine.model.Lesson;
import md.balutsel.mocointerpreter.engine.model.QuestionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_TEST_INSTANCE")
public class TestInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "score", columnDefinition = "number")
    private Double score;

    @Column(name = "name", columnDefinition = "varchar2(255)")
    private String name;

    @Column(name = "information", columnDefinition = "text")
    private String information;

    @ManyToOne
    @JoinColumn(name = "fk_lesson_instance", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private LessonInstance lessonInstance;

    @OneToMany(mappedBy = "testInstance", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionInstance> questions = new ArrayList<>();

    public void setQuestions(List<QuestionInstance> questions) {
        this.questions.addAll(questions);
        questions.forEach(questionInstance -> questionInstance.setTestInstance(this));
    }
}
