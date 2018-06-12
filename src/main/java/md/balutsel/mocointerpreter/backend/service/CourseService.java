package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.controller.dto.CourseLessonDto;
import md.balutsel.mocointerpreter.backend.model.*;
import md.balutsel.mocointerpreter.backend.repository.CourseInstanceRepository;
import md.balutsel.mocointerpreter.engine.Engine;
import md.balutsel.mocointerpreter.engine.exceptions.CourseNotFoundException;
import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private Engine engine;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    @Autowired
    private LessonService lessonService;

    public List<String> getAllCoursesNames() {
        return engine.getCourses()
                .stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }

    public List<CourseLessonDto> getAccessibleCourseLessonNames(String username, String courseName) {

        List<Integer> visitedLessons = courseInstanceRepository
                .findByCourseNameAndUsername(courseName, username)
                .getVisitedLessons();

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

    public Integer getCourseInstance(String username, String courseName) {
        CourseInstance fetchedCourseInstance = courseInstanceRepository.findByCourseNameAndUsername(courseName, username);
        if (Objects.isNull(fetchedCourseInstance)) {
            CourseInstance courseInstance = new CourseInstance();
            courseInstance.setName(courseName);
            courseInstance.setUsername(username);
            Course course = getCourse(courseName);
            courseInstance.setLessonInstances(lessonService.extractLessonInstances(course));
            courseInstanceRepository.save(courseInstance);
            return courseInstance.getLastVisitedLesson();
        } else {
            return fetchedCourseInstance.getLastVisitedLesson();
        }
    }

    public LessonInstance getLessonInstance(String username, String courseName, Integer lessonNumber) {
        return courseInstanceRepository.findByCourseNameAndUsername(courseName, username)
                .getLessonInstances()
                .stream()
                .filter(lessonInstance -> lessonInstance.getNumber().equals(lessonNumber))
                .findAny()
                .orElseThrow(GrammarException::new);
    }
}
