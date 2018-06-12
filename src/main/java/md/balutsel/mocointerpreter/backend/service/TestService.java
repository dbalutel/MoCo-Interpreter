package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.TestInstance;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.Lesson;
import md.balutsel.mocointerpreter.engine.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TestService {

    @Autowired
    private QuestionService questionService;

    public List<TestInstance> extractTestInstances(Lesson lesson) {
        if (Objects.nonNull(lesson.getTest())) {
            TestInstance testInstance = new TestInstance();
            testInstance.setName(lesson.getTest().getName());
            testInstance.setInformation(lesson.getTest().getInformation());
            testInstance.setQuestions(questionService.extractQuestionInstances(lesson.getTest()));
            return List.of(testInstance);
        } else {
            return Collections.emptyList();
        }
    }
}
