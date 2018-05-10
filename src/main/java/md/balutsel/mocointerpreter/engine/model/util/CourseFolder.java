package md.balutsel.mocointerpreter.engine.model.util;

import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public final class CourseFolder {
    private Map<String, File> images = new ConcurrentHashMap<>();
    private Map<String, File> music = new ConcurrentHashMap<>();
    private Map<String, File> videos = new ConcurrentHashMap<>();
    private File mocCourse;
}
