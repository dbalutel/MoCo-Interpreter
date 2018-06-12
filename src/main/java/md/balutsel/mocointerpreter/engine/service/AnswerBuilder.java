package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class AnswerBuilder {


    public List<Answer> createFree(List<String> questionLines) {
        var answers = new ArrayList<Answer>();

        for (var i = 0; i < questionLines.size() - 1; i++) {
            if (questionLines.get(i).matches(SINGLE_FREE_WRONG_ANSWER)) {
                var answer = buildFreeAnswer(questionLines.get(i), false);
                answers.add(answer);
                if (questionLines.get(i+1).matches(ANSWER_REACTION)) {
                    answer.setReaction(extractReaction(questionLines.get(i+1)));
                }
            } else if (questionLines.get(i).matches(SINGLE_FREE_CORRECT_ANSWER)) {
                var answer = buildFreeAnswer(questionLines.get(i), true);
                answers.add(answer);
                if (questionLines.get(i+1).matches(ANSWER_REACTION)) {
                    answer.setReaction(extractReaction(questionLines.get(i+1)));
                }
            }
        }

        return answers;
    }

    public List<Answer> createSingle(List<String> questionLines) {
        var answers = new ArrayList<Answer>();

        for (var i = 0; i < questionLines.size() - 1; i++) {
            if (questionLines.get(i).matches(SINGLE_FREE_WRONG_ANSWER)) {
                var answer = buildSingleAnswer(questionLines.get(i), false);
                answers.add(answer);
                if (questionLines.get(i+1).matches(ANSWER_REACTION)) {
                    answer.setReaction(extractReaction(questionLines.get(i+1)));
                }
            } else if (questionLines.get(i).matches(SINGLE_FREE_CORRECT_ANSWER)) {
                var answer = buildSingleAnswer(questionLines.get(i), true);
                answers.add(answer);
                if (questionLines.get(i+1).matches(ANSWER_REACTION)) {
                    answer.setReaction(extractReaction(questionLines.get(i+1)));
                }
            }
        }

        return answers;
    }

    private SingleAnswer buildSingleAnswer(String answerLine, boolean isValid) {
        var answer = new SingleAnswer();
        answer.setCorrect(isValid);
        answer.setPoints(extractPoints(answerLine));
        answer.setText(extractText(answerLine));
        return answer;
    }

    private FreeAnswer buildFreeAnswer(String answerLine, boolean isValid) {
        var answer = new FreeAnswer();
        answer.setCorrect(isValid);
        answer.setPoints(extractPoints(answerLine));
        answer.setText(extractText(answerLine));
        return answer;
    }

    private MultiAnswer buildMultiAnswer(String answerLine, boolean isValid) {
        var answer = new MultiAnswer();
        answer.setCorrect(isValid);
        answer.setText(extractText(answerLine));
        if (isValid) {
            answer.setPoints(1);
        } else {
            answer.setPoints(-1);
        }
        return answer;
    }

    private int extractPoints(String line) {
        return Integer.parseInt(line.replaceAll("\\$\\s*\\(\\s*_(Wrong|Correct)\\s*,\\s*", "")
                .replaceAll("\\s*\\)\\s*.*", ""));
    }

    private String extractText(String line) {
        return line.replaceAll("\\$\\s*\\(\\s*_(Wrong|Correct)\\s*,\\s*(-)*\\d+\\s*\\)\\s*", "")
                .replaceAll("\\$\\s*\\(\\s*_(Wrong|Correct)\\s*\\)\\s*", "");
    }

    private String extractReaction(String line) {
        return line.replaceAll("\\$\\s*\\(\\s*_Reaction\\s*\\)\\s*", "");
    }

    public List<Answer> createMultiple(List<String> questionLines) {
        var answers = new ArrayList<Answer>();

        for (var i = 0; i < questionLines.size() - 1; i++) {
            if (questionLines.get(i).matches(MULTIPLE_WRONG_ANSWER)) {
                var answer = buildMultiAnswer(questionLines.get(i), false);
                answers.add(answer);
            } else if (questionLines.get(i).matches(MULTIPLE_CORRECT_ANSWER)) {
                var answer = buildMultiAnswer(questionLines.get(i), true);
                answers.add(answer);
            }
        }

        return answers;
    }
}
