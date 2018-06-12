package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
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

    @ManyToOne
    @JoinColumn(name = "fk_test_id", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private TestInstance testInstance;

    @OneToMany(mappedBy = "questionInstance", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AnswerInstance> answers = new ArrayList<>();
}
