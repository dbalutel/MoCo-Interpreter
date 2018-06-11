package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import md.balutsel.mocointerpreter.engine.model.util.Literals;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
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
        for (var parseElement : Literals.FONT_PARSE_ELEMENTS) {
            text = parseFont(text, parseElement);
        }
        return text;
    }

    private String parseMedia(String text, CourseFolder courseFolder, ParseMediaElement parseMediaElement) {
        Map<String, String> literals = findMediaLiterals(text, parseMediaElement);

        var encoder = Base64.getEncoder();

        for (var literal : literals.entrySet()) {
            try {
                switch (parseMediaElement.getLiteral()) {
                    case IMAGE_LITERAL:
                        text = text.replaceAll(Pattern.quote(literal.getValue()), parseMediaElement.getLeftPartHtmlReplacement() +
                                encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getImages()
                                        .get(literal.getKey()).toURI()))) +
                                parseMediaElement.getRightPartHtmlReplacement());
                        break;
                    case VIDEO_LITERAL:
                        text = text.replaceAll(Pattern.quote(literal.getValue()), parseMediaElement.getLeftPartHtmlReplacement() +
                                encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getVideos()
                                        .get(literal.getKey() + ".mp4").toURI()))) +
                                parseMediaElement.getRightPartHtmlReplacement());
                        break;
                    case AUDIO_LITERAL:
                        text = text.replaceAll(Pattern.quote(literal.getValue()), parseMediaElement.getLeftPartHtmlReplacement() +
                                encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getMusic()
                                        .get(literal.getKey()).toURI()))) +
                                parseMediaElement.getRightPartHtmlReplacement());
                        break;
                }
            } catch (IOException e) {
                throw new GrammarException();
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

    private String parseFont(String text, ParseTextElement parseTextElement) {
        var leftPart = findFontLiteral(text, parseTextElement.getLeftLiteral());
        var rightPart = findFontLiteral(text, parseTextElement.getRightLiteral());

        for (var literalPart : leftPart.entrySet()) {
            text = text.replaceAll(Pattern.quote(literalPart.getKey()),
                    parseTextElement.getLeftPartHtmlReplacement() + literalPart.getValue() + "px" + "\">");
        }

        for (var literalPart : rightPart.entrySet()) {
            text = text.replaceAll(Pattern.quote(literalPart.getKey()),
                    parseTextElement.getRightPartHtmlReplacement());
        }
        return text;
    }

    private Map<String, String> findFontLiteral(String text, String parseFontElement) {
        return Pattern.compile(parseFontElement)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        literal -> literal.chars()
                                .parallel()
                                .mapToObj(i -> String.valueOf((char) i))
                                .dropWhile(s -> !s.equals("="))
                                .skip(1)
                                .takeWhile(s -> !s.equals("*"))
                                .collect(Collectors.joining())
                                .trim()
                                .toLowerCase()));
    }
}