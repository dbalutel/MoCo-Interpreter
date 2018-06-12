package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;

@Data
public abstract class Answer {
    private boolean isCorrect;
    private String text;
    private String reaction;
    private int points;
}
