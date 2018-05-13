package md.balutsel.mocointerpreter.engine.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Question {
    private String questionText;

    @Setter(AccessLevel.PROTECTED)
    private QuestionType questionType;

    private int numberOfAttempts;
    private List<String> helpPhrases = new ArrayList<>();
    private String exceedPhrase;
    private List<Answer> answers = new ArrayList<>();
}
