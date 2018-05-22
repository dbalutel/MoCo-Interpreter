package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.model.util.CourseFolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class MediaDataParser {

    public String parseMedia(String text, CourseFolder courseFolder) {
        text = replaceImages(text, courseFolder);
        text = replaceVideos(text, courseFolder);
        text = replaceAudio(text, courseFolder);
        return text;
    }

    private String replaceAudio(String text, CourseFolder courseFolder) {
        Map<String, String> audioLiterals = findAudioLiterals(text);

        var encoder = Base64.getEncoder();

        for (var audioLiteral : audioLiterals.entrySet()) {
            try {
                while (text.contains(audioLiteral.getValue())) {
                    text = text.replace(audioLiteral.getValue(), "<audio controls src=\"data:audio/mp3;base64," +
                            encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getMusic()
                                    .get(audioLiteral.getKey()).toURI()))) +
                            "\"/>");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    private String replaceVideos(String text, CourseFolder courseFolder) {
        Map<String, String> videoLiterals = findVideoLiterals(text);

        var encoder = Base64.getEncoder();

        for (var videoLiteral : videoLiterals.entrySet()) {
            try {
                while (text.contains(videoLiteral.getValue())) {
                    text = text.replace(videoLiteral.getValue(), "<video controls>" +
                            "<source type=\"video/mp4\" src=\"data:video/mp4;base64," +
                            encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getVideos()
                                    .get(videoLiteral.getKey()).toURI()))) +
                            "\"/>");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    private String replaceImages(String text, CourseFolder courseFolder) {

        Map<String, String> imgLiterals = findImageLiterals(text);

        var encoder = Base64.getEncoder();

        for (var imgLiteral : imgLiterals.entrySet()) {
            try {
                while (text.contains(imgLiteral.getValue())) {
                    text = text.replace(imgLiteral.getValue(), "<img src=\"data:image/png;base64," +
                            encoder.encodeToString(Files.readAllBytes(Paths.get(courseFolder.getImages()
                                    .get(imgLiteral.getKey()).toURI()))) +
                            "\" alt=\"Image\" />");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    private Map<String, String> findAudioLiterals(String text) {
        return Pattern.compile(AUDIO_LITERAL)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toMap(s -> s.replaceAll("\\|\\s*\\*\\s*Audio\\s*=\\s*", "")
                                .replaceAll("\\.mp3\\s*\\*\\s*\\|", ""),
                        Function.identity()));
    }

    private Map<String, String> findVideoLiterals(String text) {
        return Pattern.compile(VIDEO_LITERAL)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toMap(s -> s.replaceAll("\\|\\s*\\*\\s*Video\\s*=\\s*", "")
                                .replaceAll("\\.mp4\\s*\\*\\s*\\|", ""),
                        Function.identity()));
    }

    private Map<String, String> findImageLiterals(String text) {
        return Pattern.compile(IMAGE_LITERAL)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toMap(s -> s.replaceAll("\\|\\s*\\*\\s*Image\\s*=\\s*", "")
                                .replaceAll("\\.jpg\\s*\\*\\s*\\|", ""),
                        Function.identity()));
    }
}
