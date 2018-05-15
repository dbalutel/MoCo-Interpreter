package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.TEST_SECTION;
import static md.balutsel.mocointerpreter.engine.model.util.Literals.TEST_START_LITERAL;

@Service
public class TestBuilder {

    @Autowired
    private PartBuilder partBuilder;

    public Test extractTest(String lessonString) {
        return Pattern.compile(TEST_SECTION)
                .matcher(lessonString)
                .results()
                .map(MatchResult::group)
                .parallel()
                .map(this::buildText)
                .findAny()
                .orElse(null);
    }

    private Test buildText(String testString) {
        List<String> testLines = new ArrayList<>(List.of(testString.split("\n")));

        Test test = new Test();
        test.setName(extractTestName(testLines.get(0)));
        test.setInformation(extractTestInfo(testLines.get(0)));
        test.setParts(partBuilder.extractParts(testString));

        return test;
    }

    private String extractTestName(String line) {
        if (line.matches(TEST_START_LITERAL)) {
            return line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
        } else {
            throw new GrammarException();
        }
    }

    private String extractTestInfo(String line) {
        if (line.matches(TEST_START_LITERAL)) {
                return line.substring(line.indexOf(")") + 1).trim();
        } else {
            throw new GrammarException();
        }
    }
}
