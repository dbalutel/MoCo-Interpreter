package md.balutsel.mocointerpreter.engine.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"answers"})
@ToString(exclude = {"answers"})
public abstract class Question {
    private String questionText;

    @Setter(AccessLevel.PROTECTED)
    private QuestionType questionType;

    private int numberOfAttempts;
    private List<String> helpPhrases = new ArrayList<>();
    private String exceedPhrase;
    private List<Answer> answers = new ArrayList<>();
}
