package md.balutsel.mocointerpreter.engine;

import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.engine.CourseFolder;
import md.balutsel.mocointerpreter.engine.service.CourseBuilder;
import md.balutsel.mocointerpreter.engine.service.FileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Service
public final class Engine {

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private CourseBuilder courseBuilder;

    public void startUp() {
        List<Course> courseBuilderList = courseBuilder.buildCourses(fileLoader.initializeCourseFolders());
    }
}
