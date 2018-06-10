package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.controller.dto.CourseLessonDto;
import md.balutsel.mocointerpreter.backend.repository.CourseRepository;
import md.balutsel.mocointerpreter.engine.Engine;
import md.balutsel.mocointerpreter.engine.exceptions.CourseNotFoundException;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.Lesson;
import md.balutsel.mocointerpreter.engine.model.StartUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private Engine engine;

    @Autowired
    private CourseRepository courseRepository;

    public List<String> getAllCoursesNames() {
        return engine.getCourses()
                .stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }

    public List<CourseLessonDto> getAccessibleCourseLessonNames(String username, String courseName) {

        List<Integer> visitedLessons = courseRepository.findByUsername(username).getVisitedLessons();

        return engine.getCourses().stream()
                .filter(c -> c.getCourseName().equals(courseName))
                .findFirst()
                .map(Course::getLessons)
                .orElseThrow(CourseNotFoundException::new)
                .stream()
                .map(lesson -> {
                    var isAvailable = true;
                    for (var lessonNumber : lesson.getRequiredToAccess()) {
                        if (!visitedLessons.contains(lessonNumber)) {
                            isAvailable = false;
                            break;
                        }
                    }
                    return new CourseLessonDto(lesson, isAvailable);
                }).collect(Collectors.toList());

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

    public StartUp getStartup(String courseName) {
        return getCourse(courseName).getStartUp();
    }
}
