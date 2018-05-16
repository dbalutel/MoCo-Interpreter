package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.engine.Engine;
import md.balutsel.mocointerpreter.engine.exceptions.CourseNotFoundException;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private Engine engine;

    public List<String> getAllCoursesNames() {
        return engine.getCourses()
                .stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }

    public List<Lesson> getAllCourseLessonNames(String courseName) {
        return engine.getCourses().stream()
                .filter(c -> c.getCourseName().equals(courseName))
                .findFirst()
                .map(Course::getLessons)
                .orElseThrow(CourseNotFoundException::new);

    }

    public Course getCourse(String courseName) {
        return engine.getCourses()
                .stream()
                .filter(course -> course.getCourseName().equals(courseName))
                .findFirst()
                .orElseThrow(CourseNotFoundException::new);
    }

    public boolean existsByName(String courseName) {
        return engine.getCourses()
                .stream()
                .anyMatch(course -> course.getCourseName().equals(courseName));
    }
}
