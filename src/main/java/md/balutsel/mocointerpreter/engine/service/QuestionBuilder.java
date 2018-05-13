package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.FreeQuestion;
import md.balutsel.mocointerpreter.engine.model.MultipleQuestion;
import md.balutsel.mocointerpreter.engine.model.Question;
import md.balutsel.mocointerpreter.engine.model.SingleQuestion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class QuestionBuilder {
    public List<Question> extractQuestions(String testString) {
        return Pattern.compile(QUESTION_SECTION)
                .matcher(testString)
                .results()
                .map(MatchResult::group)
                .parallel()
                .map(this::buildQuestion)
                .collect(Collectors.toList());
    }

    private Question buildQuestion(String questionString) {
        List<String> questionLines = new ArrayList<>(List.of(questionString.split("\n")));

        String questionStart = questionLines.get(0);
        if (questionStart.matches(QUESTION_START_LITERAL)) {
            if (questionStart.contains(FREE_QUESTION)) {
                FreeQuestion freeQuestion = new FreeQuestion();
                return freeQuestion;
            } else if (questionStart.contains(SINGLE_QUESTION)) {
                SingleQuestion singleQuestion = new SingleQuestion();
                return singleQuestion;
            } else  {
                MultipleQuestion multipleQuestion = new MultipleQuestion();
                return multipleQuestion;
            }
        } else {
            throw new GrammarException();
        }
    }
}
