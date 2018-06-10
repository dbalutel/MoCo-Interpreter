package md.balutsel.mocointerpreter.backend.controller.dto;

import lombok.Data;
import md.balutsel.mocointerpreter.engine.model.Lesson;

@Data
public class CourseLessonDto {
    private int lessonNumber;
    private String lessonName;
    private boolean isAccessible;

    public CourseLessonDto(Lesson lesson, boolean isAccessible) {
        this.lessonNumber = lesson.getNumber();
        this.lessonName = lesson.getName();
        this.isAccessible = isAccessible;
    }
}
