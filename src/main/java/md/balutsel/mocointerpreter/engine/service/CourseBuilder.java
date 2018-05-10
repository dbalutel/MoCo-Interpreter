package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.NoCourseFilesException;
import md.balutsel.mocointerpreter.engine.exceptions.NoDeclaredCourseException;
import md.balutsel.mocointerpreter.engine.model.Course;
import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public final class CourseBuilder {

    public List<Course> buildCourses(List<CourseFolder> courseFolders) {
        return courseFolders
                .parallelStream()
                .map(this::parseMocFile)
                .collect(Collectors.toList());
    }

    private Course parseMocFile(CourseFolder courseFolder) {
        try (Scanner scanner = new Scanner(courseFolder.getMocCourse())) {
            int lineIndex = 0;
            Course course = new Course();
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (isComment(currentLine)) {
                    continue;
                } else {
                    if (lineIndex == 0 && isCourseStarted(currentLine)) {
                        course.setCourseName(extractCourseName(currentLine));
                    } else if (lineIndex == 0) {
                        throw new NoDeclaredCourseException();
                    } else {
                        if (currentLine.matches(START_UP_LITERAL));
                    }
                }

                lineIndex++;
            }
            return course;
        } catch (FileNotFoundException e) {
            throw new NoCourseFilesException();
        }
    }

    private boolean isComment(String line) {
        return line.startsWith(COMMENT);
    }

    private boolean isCourseStarted(String line) {
        return line.matches(COURSE_START_LITERAL);
    }

    private String extractCourseName(String courseStartLine) {
        String courseName = courseStartLine.replaceAll("^_Course\\s*\\(*\\s*", "");
        return courseName.replaceAll("\\s*\\)$", "");
    }
}
