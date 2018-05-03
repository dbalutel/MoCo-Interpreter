package md.balutsel.mocointerpreter.engine.model.engine;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public final class CourseFolder {
    private List<File> images = new CopyOnWriteArrayList<>();
    private List<File> music = new CopyOnWriteArrayList<>();
    private List<File> videos = new CopyOnWriteArrayList<>();
    private File mocCourse;
}
