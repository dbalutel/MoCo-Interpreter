package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Part {
    private int toAsk;
    private List<Question> questions = new ArrayList<>();
}
