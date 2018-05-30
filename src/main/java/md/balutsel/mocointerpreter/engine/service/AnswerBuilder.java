package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerBuilder {


    public Question createFree(List<String> questionLines) {
        var question = new FreeQuestion();
        return question;
    }

    public List<Answer> forSingleQuestion(List<String> questionLines) {
        var answers = new ArrayList<>();

        for (var i = 0; i < questionLines.size() - 1; i++) {

        }

        return null;
    }

    public Question createMultiple(List<String> questionLines) {
        var question = new MultipleQuestion();
        return question;
    }
}
