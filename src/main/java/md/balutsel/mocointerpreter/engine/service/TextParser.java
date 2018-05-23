package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import md.balutsel.mocointerpreter.engine.model.util.Literals;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class TextParser {

    public String parse(String text, CourseFolder courseFolder) {
        for (var parseElement : Literals.MEDIA_PARSE_ELEMENTS) {
            text = parseMedia(text, courseFolder, parseElement);
        }
        for (var parseElement : Literals.TEXT_PARSE_ELEMENTS) {
            text = parseText(text, parseElement);
        }
        return text;
    }

    private String parseMedia(String text, CourseFolder courseFolder, ParseMediaElement parseMediaElement) {

        Map<String, String> literals = findMediaLiterals(text, parseMediaElement);

        var encoder = Base64.getEncoder();

        for (var literal : literals.entrySet()) {
            try {
                text = text.replaceAll(Pattern.quote(literal.getValue()), parseMediaElement.getLeftPartHtmlReplacement() +
                        encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getImages()
                                .get(literal.getKey()).toURI()))) +
                        parseMediaElement.getRightPartHtmlReplacement());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    private Map<String, String> findMediaLiterals(String text, ParseMediaElement parseMediaElement) {
        return Pattern.compile(parseMediaElement.getLiteral())
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toMap(s -> s.replaceAll(parseMediaElement.getLiteralLeftPartReplacement(), "")
                                .replaceAll(parseMediaElement.getLiteralRightPartReplacement(), ""),
                        Function.identity()));
    }

    private String parseText(String text, ParseTextElement parseMediaElement) {
        return text.replaceAll(parseMediaElement.getLeftLiteral(), parseMediaElement.getLeftPartHtmlReplacement())
                .replaceAll(parseMediaElement.getRightLiteral(), parseMediaElement.getRightPartHtmlReplacement());
    }
}
