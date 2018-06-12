package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.LessonInstance;
import md.balutsel.mocointerpreter.engine.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    @Autowired
    private TestService testService;

    public List<LessonInstance> extractLessonInstances(Course course) {
        return course.getLessons()
                .parallelStream()
                .map(lesson -> {
                    LessonInstance lessonInstance = new LessonInstance();
                    lessonInstance.setName(lesson.getName());
                    lessonInstance.setContent(lesson.getInformation());
                    lessonInstance.setNumber(lesson.getNumber());
                    lessonInstance.setRequiredToAccess(new ArrayList<>(lesson.getRequiredToAccess()));
                    lessonInstance.setTestInstances(testService.extractTestInstances(lesson));
                    return lessonInstance;
                })
                .collect(Collectors.toList());
    }
}
