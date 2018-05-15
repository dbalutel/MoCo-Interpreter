package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.engine.Engine;
import md.balutsel.mocointerpreter.engine.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private Engine engine;

    public List<String> getAllCoursesNames() {
        return engine.getCourses().stream().map(Course::getCourseName).collect(Collectors.toList());
    }
}
