package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MultiAnswer extends Answer {
    private int score;
}
