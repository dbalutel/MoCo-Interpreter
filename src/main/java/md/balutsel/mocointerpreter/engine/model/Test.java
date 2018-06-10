package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(exclude = {"parts"})
@ToString(exclude = {"parts"})
public class Test {
    private String name;
    private String information;
    private List<Part> parts = new ArrayList<>();
    private Map<Integer, String> evaluations = new HashMap<>();
}
