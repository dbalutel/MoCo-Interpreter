package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.exceptions.NoStrartUpSection;
import md.balutsel.mocointerpreter.engine.model.StartUp;
import org.springframework.stereotype.Service;

import java.util.List;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.END_START_UP_LITERAL;
import static md.balutsel.mocointerpreter.engine.model.util.Literals.START_UP_LITERAL;

@Service
public class StartUpBuilder {

    public StartUp extractStartUp(List<String> fileLines) {
        StartUp startUp = new StartUp();
        startUp.setStartUpText(validateStartUp(fileLines));
        return startUp;
    }

    private String validateStartUp(List<String> fileLines) {
        if (fileLines.get(1).equals(START_UP_LITERAL)) {
            try {
                List<String> startUpSection = fileLines.subList(fileLines.indexOf(START_UP_LITERAL),
                        fileLines.indexOf(END_START_UP_LITERAL) + 1);

                List<String> startUpText = startUpSection.subList(1, startUpSection.size() - 1);

                if (startUpText.stream().anyMatch(textLine -> textLine.startsWith("_"))) {
                    throw new GrammarException();
                } else {
                    return startUpText.stream().reduce(" ", String::concat);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                throw new NoStrartUpSection();
            }
        } else {
            throw new NoStrartUpSection();
        }

    }
}
