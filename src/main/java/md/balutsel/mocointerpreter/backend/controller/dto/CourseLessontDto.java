package md.balutsel.mocointerpreter.backend.controller.dto;

import lombok.Data;
import md.balutsel.mocointerpreter.engine.model.Lesson;

@Data
public class CourseLessontDto {
    private int lessonNumber;
    private String lessonName;

    public CourseLessontDto(Lesson lesson) {
        this.lessonNumber = lesson.getNumber();
        this.lessonName = lesson.getName();
    }
}
