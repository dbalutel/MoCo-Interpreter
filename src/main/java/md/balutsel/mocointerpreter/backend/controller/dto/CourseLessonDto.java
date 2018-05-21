package md.balutsel.mocointerpreter.backend.controller.dto;

import lombok.Data;
import md.balutsel.mocointerpreter.engine.model.Lesson;

@Data
public class CourseLessonDto {
    private int lessonNumber;
    private String lessonName;

    public CourseLessonDto(Lesson lesson) {
        this.lessonNumber = lesson.getNumber();
        this.lessonName = lesson.getName();
    }
}
