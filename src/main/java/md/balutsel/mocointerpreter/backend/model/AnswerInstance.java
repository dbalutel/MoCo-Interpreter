package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_ANSWER_INSTANCE")
public class AnswerInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "is_marked")
    private Boolean isMarked = false;

    @ManyToOne
    @JoinColumn(name = "fk_question_id", referencedColumnName = "id")
    @JsonBackReference
    private QuestionInstance questionInstance;
}
