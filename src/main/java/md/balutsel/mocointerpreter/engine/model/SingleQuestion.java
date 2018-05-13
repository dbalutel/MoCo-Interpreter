package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleQuestion extends Question {
    public SingleQuestion() {
        this.setQuestionType(QuestionType.SINGLE);
    }
}
