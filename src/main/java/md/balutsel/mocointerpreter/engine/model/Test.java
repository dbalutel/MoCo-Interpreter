package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Test {
    private String name;
    private String information;
    private List<Part> parts = new ArrayList<>();
    private Map<Integer, String> evaluations = new HashMap<>();
}
