package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultipleQuestion extends Question {
    public MultipleQuestion() {
        this.setQuestionType(QuestionType.MULTIPLE);
    }
}
