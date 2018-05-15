package md.balutsel.mocointerpreter.backend.controller;

import md.balutsel.mocointerpreter.backend.service.CourseService;
import md.balutsel.mocointerpreter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/login/{username}/course/{courseName}")
    @ResponseBody
    public String logIn(@PathVariable String username, @PathVariable String courseName) {
        return userService.logIn(username);
    }

    @GetMapping("/api/courses")
    @ResponseBody
    public List<String> getAllCoursesNames() {
        return courseService.getAllCoursesNames();
    }

}
