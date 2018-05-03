package md.balutsel.mocointerpreter.engine.service;


import md.balutsel.mocointerpreter.engine.exceptions.NoCourseFilesException;
import md.balutsel.mocointerpreter.engine.model.engine.CourseFolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.engine.FileType.*;

@Service
public final class FileLoader {

    private static final String PATHNAME = "courses";

    public List<CourseFolder> initializeCourseFolders() {
        File courseFolder = new File(PATHNAME);
        File[] courses = courseFolder.listFiles();

        if (Objects.isNull(courses)) {
            throw new NoCourseFilesException();
        } else {
            return scanCourseFolder(courses);
        }
    }

    private List<CourseFolder> scanCourseFolder(File[] courses) {
        return Arrays.stream(courses)
                .parallel()
                .map(File::listFiles)
                .filter(Objects::nonNull)
                .map(files -> {
                    CourseFolder courseFolder = new CourseFolder();
                    Arrays.stream(files)
                            .parallel()
                            .forEach(file -> this.mapCourseFolder(courseFolder, file));
                    return courseFolder;
                })
                .collect(Collectors.toList());
    }

    private void mapCourseFolder(CourseFolder courseFolder, File file) {

        String fileName = file.getName();

        if (fileName.endsWith(JPG)) {
            courseFolder.getImages().add(file);
        } else if (fileName.endsWith(MP3)) {
            courseFolder.getMusic().add(file);
        } else if (fileName.endsWith(MP4)) {
            courseFolder.getVideos().add(file);
        } else if (fileName.endsWith(MOC)){
            courseFolder.setMocCourse(file);
        }

    }
}
