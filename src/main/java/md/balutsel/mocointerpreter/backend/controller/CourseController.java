package md.balutsel.mocointerpreter.backend.controller;

import md.balutsel.mocointerpreter.backend.controller.dto.CourseLessonDto;
import md.balutsel.mocointerpreter.backend.controller.dto.LoginVisitedLesson;
import md.balutsel.mocointerpreter.backend.model.CourseInstance;
import md.balutsel.mocointerpreter.backend.model.LessonInstance;
import md.balutsel.mocointerpreter.backend.repository.CourseInstanceRepository;
import md.balutsel.mocointerpreter.backend.service.CourseService;
import md.balutsel.mocointerpreter.backend.service.UserService;
import md.balutsel.mocointerpreter.engine.model.StartUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    @GetMapping("/api/login/{username}/course/{courseName}")
    public LoginVisitedLesson logIn(@PathVariable String username, @PathVariable String courseName) {
        return userService.logIn(username, courseName);
    }

    @GetMapping("/api/courses")
    public List<String> getAllCoursesNames() {
        return courseService.getAllCoursesNames();
    }

    @GetMapping("/api/{username}/courses/{courseName}/lessons")
    public List<CourseLessonDto> getCourseLessons(@PathVariable String username, @PathVariable String courseName) {
        return courseService.getAccessibleCourseLessonNames(username, courseName);
    }

    @GetMapping("/api/{username}/courses/{courseName}/lessons/{lessonNumber}")
    public LessonInstance getLessonInstance(@PathVariable String username, @PathVariable String courseName, @PathVariable Integer lessonNumber) {
        return courseService.getLessonInstance(username, courseName, lessonNumber);
    }

    @GetMapping("/api/courses/{courseName}/startup")
    public StartUp getCourseStartup(@PathVariable String courseName) {
        return courseService.getStartup(courseName);
    }

    @GetMapping("/demo")
    public LessonInstance get() {
        return courseInstanceRepository.findAll().get(0).getLessonInstances().get(0);
    }
}
