package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.Part;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.PART_SECTION;
import static md.balutsel.mocointerpreter.engine.model.util.Literals.PART_START_LITERAL;
import static md.balutsel.mocointerpreter.engine.model.util.Literals.TEST_SECTION;

@Service
public class PartBuilder {

    public List<Part> extractParts(String testString) {
        return Pattern.compile(PART_SECTION)
                .matcher(testString)
                .results()
                .map(MatchResult::group)
                .parallel()
                .map(this::buildPart)
                .collect(Collectors.toList());
    }

    private Part buildPart(String testString) {
        List<String> partLines = new ArrayList<>(List.of(testString.split("\n")));

        Part part = new Part();
        part.setToAsk(extractHowManyToAsk(partLines.get(0)));
        part.setQuestions(null);

        return part;
    }

    private int extractHowManyToAsk(String line) {
        if (line.matches(PART_START_LITERAL)) {
            String numberOfQuestions = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
            if (numberOfQuestions.chars()
                .allMatch(Character::isDigit)) {
                return Integer.parseInt(numberOfQuestions);
            } else {
                return -1;
            }
        } else {
            throw new GrammarException();
        }
    }
}
