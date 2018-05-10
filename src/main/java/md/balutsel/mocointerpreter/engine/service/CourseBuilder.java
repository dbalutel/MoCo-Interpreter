package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.NoCourseFilesException;
import md.balutsel.mocointerpreter.engine.exceptions.NoDeclaredCourseException;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public final class CourseBuilder {

    @Autowired
    private StartUpBuilder startUpBuilder;

    @Autowired
    private LessonBuilder lessonBuilder;

    public List<Course> buildCourses(List<CourseFolder> courseFolders) {
        return courseFolders
                .parallelStream()
                .map(this::parseMocFile)
                .collect(Collectors.toList());
    }

    private Course parseMocFile(CourseFolder courseFolder) {
        try {

            List<String> fileLines = Files.lines(Paths.get(courseFolder.getMocCourse().toURI()))
                    .filter(this::notComment)
                    .map(String::trim)
                    .collect(Collectors.toList());

            Course course = new Course();
            course.setCourseName(extractCourseName(fileLines));
            course.setStartUp(startUpBuilder.extractStartUp(fileLines));
            course.setLessons(lessonBuilder.extractLessons(fileLines));

            return course;
        } catch (IOException e) {
            throw new NoCourseFilesException();
        }
    }

    private boolean notComment(String line) {
        return !line.startsWith(COMMENT);
    }

    private String extractCourseName(List<String> fileLines) {
        fileLines.forEach(System.out::println);
        if (fileLines.get(0).matches(COURSE_START_LITERAL) && fileLines.get(fileLines.size() - 1).equals(COURSE_END_LITERAL)) {
            String courseName = fileLines.get(0).replaceAll("^\\s*_Course\\s*\\(*\\s*\"", "");
            return courseName.replaceAll("\"\\s*\\)\\s*$", "");
        } else {
            throw new NoDeclaredCourseException();
        }
    }


}
