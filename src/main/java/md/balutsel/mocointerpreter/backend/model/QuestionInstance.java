package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import md.balutsel.mocointerpreter.engine.model.QuestionType;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "content", columnDefinition = "varchar2(255)")
    private String content;

    @Column(name = "is_answered")
    private Boolean isAnswered = false;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(name = "number_of_attempts")
    private Integer numberOfAttempts;

    @Column(name = "exceed_phrase", columnDefinition = "text")
    private String exceedPhrase;

    @ElementCollection
    @Column(name = "help_phrases")
    List<String> helpPhrases = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_test_id", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private TestInstance testInstance;

    @OneToMany(mappedBy = "questionInstance", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AnswerInstance> answers = new ArrayList<>();

    public void setAnswers(List<AnswerInstance> answers) {
        this.answers.addAll(answers);
        answers.forEach(answerInstance -> answerInstance.setQuestionInstance(this));
    }

    public void setHelp(List<String> helpPhrases) {
        this.helpPhrases.addAll(helpPhrases);
    }
}
