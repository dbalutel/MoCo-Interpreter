package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private String courseName;
    private StartUp startUp;
    List<Lesson> lessons;
}
