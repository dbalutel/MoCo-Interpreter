package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"questions"})
@ToString(exclude = {"questions"})
public class Part {
    private int toAsk;
    private List<Question> questions = new ArrayList<>();
}
