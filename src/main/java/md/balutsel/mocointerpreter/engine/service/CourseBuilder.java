package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.engine.CourseFolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class CourseBuilder {

    public List<Course> buildCourses(List<CourseFolder> courseFolders) {
        return courseFolders
                .parallelStream()
                .map(this::parseCourse)
                .collect(Collectors.toList());
    }

    private Course parseCourse(CourseFolder courseFolder) {
        return null;
    }
}
