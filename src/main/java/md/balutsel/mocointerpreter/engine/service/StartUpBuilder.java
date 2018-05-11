package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.exceptions.NoStrartUpSection;
import md.balutsel.mocointerpreter.engine.model.StartUp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class StartUpBuilder {

    public StartUp extractStartUp(String reducedLines) {
        StartUp startUp = new StartUp();
        startUp.setStartUpText(extractText(reducedLines));
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
