package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FreeQuestion extends Question {
    private List<String> escapeChars = new ArrayList<>();
    private String undefinedPhrase;

    public FreeQuestion() {
        this.setQuestionType(QuestionType.FREE);
    }
}

