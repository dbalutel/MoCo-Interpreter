package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.TEST_EVALUATION_LITERAL;
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
                .map(this::buildTest)
                .findAny()
                .orElse(null);
    }

    private Test buildTest(String testString) {
        List<String> testLines = new ArrayList<>(List.of(testString.split("\n")));
        
        var test = new Test();
        test.setName(extractTestName(testLines.get(0)));
        test.setInformation(extractTestInfo(testLines.get(0)));
        test.setParts(partBuilder.extractParts(testString));
        test.setEvaluations(extractEvaluations(testLines));

        return test;
    }

    private Map<Integer, String> extractEvaluations(List<String> testLines) {
        var evaluations = new HashMap<Integer, String>();

        for (var testLine : testLines) {
            if (testLine.matches(TEST_EVALUATION_LITERAL)) {
                var evaluationCaption = testLine.replaceAll("_Evaluate\\s*\\(\\s*(\\s*(\\d+|\\d+\\.\\.\\d+|_Other))\\s*\\)\\s*", "");
                var gradesCaption = testLine.replaceAll("_Evaluate\\s*\\(\\s*", "")
                        .replaceAll("\\s*\\).*", "");

                if (gradesCaption.matches("\\d+")) {
                    evaluations.put(Integer.parseInt(gradesCaption), evaluationCaption);
                } else if (gradesCaption.matches("\\d+\\.\\.\\d+")) {
                    var lowerBound = Integer.parseInt(gradesCaption.replaceAll("\\.\\.\\d+", ""));
                    var upperBound = Integer.parseInt(gradesCaption.replaceAll("\\d+\\.\\.", ""));
                    for (var i = lowerBound; i <= upperBound; i++) {
                        evaluations.put(i, evaluationCaption);
                    }
                } else {
                    evaluations.put(-1, evaluationCaption);
                }
            }
        }

        return evaluations;
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
