package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.QuestionInstance;
import md.balutsel.mocointerpreter.engine.model.Question;
import md.balutsel.mocointerpreter.engine.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private AnswerService answerService;

    public List<QuestionInstance> extractQuestionInstances(Test test) {
        return test.getParts()
                .stream()
                .flatMap(part -> {
                    var questions = new ArrayList<Question>(part.getQuestions());
                    Collections.shuffle(questions);
                    return questions
                            .parallelStream()
                            .limit(part.getToAsk())
                            .map(question -> {
                                QuestionInstance questionInstance = new QuestionInstance();
                                questionInstance.setContent(question.getQuestionText());
                                questionInstance.setQuestionType(question.getQuestionType());
                                questionInstance.setHelpPhrases(question.getHelpPhrases());
                                questionInstance.setNumberOfAttempts(question.getNumberOfAttempts());
                                questionInstance.setExceedPhrase(question.getExceedPhrase());
                                questionInstance.setAnswers(answerService.extractAnswerInstances(question));
                                return questionInstance;
                            });
                }).collect(Collectors.toList());
    }
}
