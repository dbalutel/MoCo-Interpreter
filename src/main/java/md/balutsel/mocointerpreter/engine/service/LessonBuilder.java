package md.balutsel.mocointerpreter.engine.service;

import md.balutsel.mocointerpreter.engine.exceptions.GrammarException;
import md.balutsel.mocointerpreter.engine.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.balutsel.mocointerpreter.engine.model.util.Literals.*;

@Service
public class LessonBuilder {

    @Autowired
    private TestBuilder testBuilder;

    public List<Lesson> extractLessons(String reducedLines) {
        return Pattern.compile(LESSON_SECTION)
                .matcher(reducedLines)
                .results()
                .map(MatchResult::group)
                .parallel()
                .map(this::buildLesson)
                .collect(Collectors.toList());
    }

    private Lesson buildLesson(String lessonString) {
        List<String> lessonLines = new ArrayList<>(List.of(lessonString.split("\n")));

        Lesson lesson = new Lesson();
        lesson.setNumber(extractLessonNumber(lessonLines.get(0)));
        lesson.setName(extractLessonName(lessonLines.get(0)));
        lesson.setRequiredToAccess(extractRequiredToAccess(lessonLines.get(0)));
        lesson.setInformation(extractInformation(lessonString));
        lesson.setTest(testBuilder.extractTest(lessonString));

        return lesson;
    }

    private int extractLessonNumber(String line) {
        if (line.matches(LESSON_START_LITERAL)) {
            String number = line.chars()
                    .dropWhile(c -> !Character.isDigit(c))
                    .takeWhile(Character::isDigit)
                    .mapToObj(i -> String.valueOf((char) i))
                    .collect(Collectors.joining(""));
            return Integer.parseInt(number);
        } else {
            throw new GrammarException();
        }
    }

    private String extractLessonName(String line) {
        if (line.matches(LESSON_START_LITERAL)) {
            return line.chars()
                    .mapToObj(i -> String.valueOf((char) i))
                    .dropWhile(s -> !s.equals("\""))
                    .skip(1)
                    .takeWhile(s -> !s.equals("\""))
                    .collect(Collectors.joining(""));
        } else {
            throw new GrammarException();
        }
    }

    private Set<Integer> extractRequiredToAccess(String line) {
        if (line.matches(LESSON_START_LITERAL)) {
            Set<Integer> requiredLessonsList = new HashSet<>();

            String requiredLessons = line.chars()
                    .mapToObj(i -> String.valueOf((char) i))
                    .dropWhile(s -> !s.equals("["))
                    .skip(1)
                    .takeWhile(s -> !s.equals("]"))
                    .filter(s -> !s.equals(" "))
                    .collect(Collectors.joining(""));

            String[] splicedLessons = requiredLessons.split(",");

            for (String interval : splicedLessons) {
                if(interval.matches("^\\d+$")) {
                    requiredLessonsList.add(Integer.parseInt(interval));
                } else if (interval.matches("^\\d+..\\d+")) {
                    String[] slicedInterval = interval.split("\\.\\.");
                    for (int i = Integer.parseInt(slicedInterval[0]); i <= Integer.parseInt(slicedInterval[1]); i++) {
                        requiredLessonsList.add(i);
                    }
                }
            }
            return requiredLessonsList;
        } else {
            throw new GrammarException();
        }
    }

    private String extractInformation(String lessonString) {
        return Pattern.compile(INFORMATION_SECTION)
                .matcher(lessonString)
                .results()
                .map(MatchResult::group)
                .findAny()
                .orElseThrow(GrammarException::new)
                .replaceAll(INFORMATION_START_LITERAL, "")
                .replaceAll(INFORMATION_END_LITERAL, "");
    }
}
