package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.NoCourseFilesException;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.COMMENT;

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
                    .map(String::trim)
                    .filter(this::notComment)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            String reducedLines = fileLines.parallelStream().collect(Collectors.joining("\n"));

            Course course = new Course();
            course.setCourseName(extractCourseName(fileLines));
            course.setStartUp(startUpBuilder.extractStartUp(reducedLines, courseFolder));
            course.setLessons(lessonBuilder.extractLessons(reducedLines, courseFolder));

            return course;
        } catch (IOException e) {
            throw new NoCourseFilesException();
        }
    }

    private boolean notComment(String line) {
        return !line.startsWith(COMMENT);
    }

    private String extractCourseName(List<String> fileLines) {
        return fileLines.get(0)
                .replaceAll("^_Course\\s*\\(*\\s*\"", "")
                .replaceAll("\"\\s*\\)$", "");
    }
}
