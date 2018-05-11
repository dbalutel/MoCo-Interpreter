package md.balutsel.mocointerpreter.engine.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Test {
    private String name;
    private String information;
    private List<Part> parts = new ArrayList<>();
}
