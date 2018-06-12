package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.AnswerInstance;
import md.balutsel.mocointerpreter.engine.model.Answer;
import md.balutsel.mocointerpreter.engine.model.Question;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AnswerService {
    public List<AnswerInstance> extractAnswerInstances(Question question) {
        return Collections.emptyList();
    }
}
