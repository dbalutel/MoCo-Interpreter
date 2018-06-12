package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.AnswerInstance;
import md.balutsel.mocointerpreter.engine.model.Answer;
import md.balutsel.mocointerpreter.engine.model.Question;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    public List<AnswerInstance> extractAnswerInstances(Question question) {
        return question.getAnswers()
                .stream()
                .map(answer -> {
                    AnswerInstance answerInstance = new AnswerInstance();
                    answerInstance.setContent(answer.getText());
                    answerInstance.setReaction(answer.getReaction());
                    answerInstance.setIsMarked(false);
                    answerInstance.setIsCorrect(answer.isCorrect());
                    return answerInstance;
                })
                .collect(Collectors.toList());
    }
}
