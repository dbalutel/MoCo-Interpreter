package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.StartUp;
import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class StartUpBuilder {

    @Autowired
    private TextParser textParser;

    public StartUp extractStartUp(String reducedLines, CourseFolder courseFolder) {
        StartUp startUp = new StartUp();
        startUp.setStartUpText(textParser.parse(extractText(reducedLines), courseFolder));
        return startUp;
    }

    private String extractText(String reducedLines) {
        return Pattern.compile(START_UP_SECTION)
                .matcher(reducedLines)
                .results()
                .map(MatchResult::group)
                .map(s -> s.replaceAll(START_UP_LITERAL, "").replaceAll(END_START_UP_LITERAL, ""))
                .findAny()
                .orElseThrow(GrammarException::new);
    }
}
