package md.balutsel.mocointerpreter.backend.controller.dto;

import lombok.Data;

@Data
public class LoginVisitedLesson {
    private String username;
    private Integer lastVisitedLesson;

    public LoginVisitedLesson(String username, Integer lastVisitedLesson) {
        this.username = username;
        this.lastVisitedLesson = lastVisitedLesson;
    }
}
