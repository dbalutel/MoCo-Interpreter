package md.balutsel.mocointerpreter.engine;

import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.service.CourseBuilder;
import md.balutsel.mocointerpreter.engine.service.FileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class Engine {

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private CourseBuilder courseBuilder;

    private List<Course> courses;

    public void startUp() {
        courses = courseBuilder.buildCourses(fileLoader.initializeCourseFolders());
    }

    public List<Course> getCourses() {
        return this.courses;
    }
}
