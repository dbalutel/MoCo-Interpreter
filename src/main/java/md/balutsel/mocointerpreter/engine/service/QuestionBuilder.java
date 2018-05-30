package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.FreeQuestion;
import md.balutsel.mocointerpreter.engine.model.MultipleQuestion;
import md.balutsel.mocointerpreter.engine.model.Question;
import md.balutsel.mocointerpreter.engine.model.SingleQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class QuestionBuilder {

    @Autowired
    private AnswerBuilder answerBuilder;

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

        var questionStart = questionLines.get(0);
        if (questionStart.matches(QUESTION_START_LITERAL)) {
            if (questionStart.contains(FREE_QUESTION)) {
                var freeQuestion = new FreeQuestion();
                setCommon(freeQuestion, questionLines);
               // freeQuestion.setAnswers(answerBuilder.createFree());
                answerBuilder.createFree(questionLines);
                return freeQuestion;
            } else if (questionStart.contains(SINGLE_QUESTION)) {
                var singleQuestion = new SingleQuestion();
                setCommon(singleQuestion, questionLines);
                singleQuestion.setAnswers(answerBuilder.forSingleQuestion(questionLines));
                return singleQuestion;
            } else {
                var multipleQuestion = new MultipleQuestion();
                setCommon(multipleQuestion, questionLines);
            //    multipleQuestion.setAnswers(answerBuilder.createMultiple(questionLines));
                return multipleQuestion;
            }
        } else {
            throw new GrammarException();
        }
    }

    private void setCommon(Question question, List<String> questionLines) {
        question.setQuestionText(extractQuestionText(questionLines));
        question.setNumberOfAttempts(extractNumberOfAttempts(questionLines));
        question.setHelpPhrases(extractHelpPhrases(questionLines));
        question.setExceedPhrase(extractExceedPhrase(questionLines));
    }

    private List<String> extractHelpPhrases(List<String> questionLines) {
        return questionLines
                .stream()
                .filter(s -> s.matches(HELP_LITERAL))
                .map(s -> s.replaceAll("\\$\\(_Help\\)\\s*", ""))
                .collect(Collectors.toList());
    }

    private String extractExceedPhrase(List<String> questionLines) {
        return questionLines
                .stream()
                .filter(s -> s.matches(EXCEED_LITERAL))
                .findAny()
                .orElse("");
    }

    private String extractQuestionText(List<String> questionLines) {
        return questionLines
                .stream()
                .filter(s -> s.matches(QUESTION_START_LITERAL))
                .findAny()
                .orElseThrow(GrammarException::new)
                .replaceAll(".*\\)\\s*", "");
    }

    private int extractNumberOfAttempts(List<String> questionLines) {
        return Integer.parseInt(questionLines
                .stream()
                .filter(s -> s.matches(QUESTION_START_LITERAL))
                .findAny()
                .orElseThrow(GrammarException::new)
                .replaceAll("_Question\\s*\\(_(Free|Multiple|Single),\\s*", "")
                .replaceAll("\\).*", ""));
    }
}
