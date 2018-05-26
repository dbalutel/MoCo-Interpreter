package md.balutsel.mocointerpreter.backend.model;

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

    @ManyToOne
    @JoinColumn(name = "fk_question_id", referencedColumnName = "id")
    private QuestionInstance question;
}
