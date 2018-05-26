package md.balutsel.mocointerpreter.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_QUESTION_INSTANCE")
public class QuestionInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_test_id", referencedColumnName = "id", nullable = false, updatable = false)
    private TestInstance test;

    @OneToMany(mappedBy = "question")
    private List<AnswerInstance> answers = new ArrayList<>();
}
