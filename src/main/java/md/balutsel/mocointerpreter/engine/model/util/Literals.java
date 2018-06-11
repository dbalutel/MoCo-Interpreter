package md.balutsel.mocointerpreter.engine.model.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public final class Literals {
    public static final String COMMENT = "@";

    public static final String COURSE_START_LITERAL = "^_Course\\s*\\(\\s*\".*\"\\s*\\)$";
    public static final String COURSE_END_LITERAL = "^_End_of_Course$";
    public static final String COURSE_SECTION = "^\\s*_Course\\s*\\(\\s*\".*\"\\s*\\)\\n(.*\\n)*_End_of_Course$";

    public static final String START_UP_LITERAL = "^_Start_Up\n";
    public static final String END_START_UP_LITERAL = "\n_End_Start_Up$";
    public static final String START_UP_SECTION = "_Start_Up\\n(.*\\n)*_End_Start_Up";

    public static final String LESSON_START_LITERAL = "^_Lesson\\s*\\(\\s*\\d+\\s+\".*\"(\\s*|\\s+\\[\\s*((\\d+|\\d+\\.\\.\\d+)(,\\s*(\\d+|\\d+\\.\\.\\d+))*)\\s*\\])\\s*\\)$";
    public static final String LESSON_END_LITERAL = "_End_of_Lesson";
    public static final String LESSON_SECTION = "_Lesson\\s*\\(\\s*\\d+\\s+\".*\"(\\s*|\\s+\\[\\s*((\\d+|\\d+\\.\\.\\d+)(,\\s*(\\d+|\\d+\\.\\.\\d+))*)\\s*\\])\\s*\\)\\n((?!_End_of_Lesson).*\\n)*_End_of_Lesson";

    public static final String INFORMATION_START_LITERAL = "^_Information";
    public static final String INFORMATION_END_LITERAL = "_End_of_Information$";
    public static final String INFORMATION_SECTION = "_Information\\n(.*\\n)*_End_of_Information";

    public static final String TEST_START_LITERAL = "^_Test\\s*\\(\\s*\".*\"\\s*\\)\\s*.*$";
    public static final String TEST_SECTION = "_Test\\s*\\(\\s*\".*\"\\s*\\)\\s*.*\\n(.*\\n)((?!_End_of_Test).*\\n)*_End_of_Test";

    public static final String TEST_EVALUATION_LITERAL = "_Evaluate\\s*\\(\\s*(\\s*(\\d+|\\d+\\.\\.\\d+|_Other))\\s*\\).*$";

    public static final String PART_START_LITERAL = "^_Part\\s*\\((\\d+|All)\\)$";
    public static final String PART_SECTION = "_Part\\s*\\((\\d+|All)\\)\\n(.*\\n)((?!_End_of_Part).*\\n)*_End_of_Part";

    public static final String QUESTION_START_LITERAL = "^_Question\\s*\\(\\s*_(Single|Free|Multiple)\\s*,\\s*\\d+\\s*\\)\\s*.*$";
    public static final String QUESTION_SECTION = "_Question\\s*\\(\\s*(_Single|_Free|_Multiple)\\s*,\\s*\\d+\\s*\\)\\s*.*\\n((?!_End_of_Question).*\\n)*_End_of_Question";

    public static final String HELP_LITERAL = "^\\s*\\$\\s*\\(\\s*_Help\\s*\\).*$";
    public static final String EXCEED_LITERAL = "^\\s*\\$\\s*\\(\\s*_Exceed\\s*\\).*$";

    public static final String FREE_QUESTION = "_Free";
    public static final String SINGLE_QUESTION = "_Single";
    public static final String MULTIPLE_QUESTION = "_Multiple";

    public static final String AUDIO_LITERAL = "\\|\\s*\\*\\s*Audio\\s*=\\s*.*\\.mp3\\s*\\*\\s*\\|";
    public static final String VIDEO_LITERAL = "\\|\\s*\\*\\s*Video\\s*=\\s*.*\\.mp4\\s*\\*\\s*\\|";
    public static final String IMAGE_LITERAL = "\\|\\s*\\*\\s*Image\\s*=\\s*.*\\.jpg\\s*\\*\\s*\\|";
    public static final String FONT_SIZE_LITERAL = "\\|\\s*\\*\\s*Size\\s*=\\s*\\d+\\.jpg\\s*\\*\\s*>\\s*\\|";
    public static final String FONT_COLOR_LITERAL = "\\|\\s*\\*\\s*Font_Color\\s*=\\s*(Red|Green|Blue|Yellow|Black|White|Magenta|Brown|Cyan|Gray)\\s*\\*\\s*\\|";

    public static final List<ParseMediaElement> MEDIA_PARSE_ELEMENTS = List.of(
            ParseMediaElement.builder()
                    .leftPartHtmlReplacement("<div style=\"display:flex;justify-content:center\"><audio controls src=\"data:audio/mp3;base64,")
                    .rightPartHtmlReplacement("\"/></div>")
                    .literal(AUDIO_LITERAL)
                    .literalLeftPartReplacement("\\|\\s*\\*\\s*Audio\\s*=\\s*")
                    .literalRightPartReplacement("\\.mp3\\s*\\*\\s*\\|")
                    .build(),
            ParseMediaElement.builder()
                    .leftPartHtmlReplacement("<div style=\"display:flex;justify-content:center\"><video controls><source type=\"video/mp4\" src=\"data:video/mp4;base64,")
                    .rightPartHtmlReplacement("\"/></div>")
                    .literal(VIDEO_LITERAL)
                    .literalLeftPartReplacement("\\|\\s*\\*\\s*Video\\s*=\\s*")
                    .literalRightPartReplacement("\\.mp4\\s*\\*\\s*\\|")
                    .build(),
            ParseMediaElement.builder()
                    .leftPartHtmlReplacement("<div style=\"display:flex;justify-content:center\"><img src=\"data:image/png;base64,")
                    .rightPartHtmlReplacement("\" alt=\"Image\" /></div>")
                    .literal(IMAGE_LITERAL)
                    .literalLeftPartReplacement("\\|\\s*\\*\\s*Image\\s*=\\s*")
                    .literalRightPartReplacement("\\.jpg\\s*\\*\\s*\\|")
                    .build()
    );

    public static final List<ParseTextElement> TEXT_PARSE_ELEMENTS = List.of(
            ParseTextElement.builder()
                    .leftPartHtmlReplacement("<b>")
                    .rightPartHtmlReplacement("</b>")
                    .leftLiteral("\\|\\s*\\*\\s*Font\\s*=\\s*B\\s*\\*\\s*>\\s*\\|")
                    .rightLiteral("\\|\\s*<\\s*\\*\\s*Font\\s*=\\s*B\\s*\\*\\s*\\|")
                    .build(),
            ParseTextElement.builder()
                    .leftPartHtmlReplacement("<i>")
                    .rightPartHtmlReplacement("</i>")
                    .leftLiteral("\\|\\s*\\*\\s*Font\\s*=\\s*I\\s*\\*\\s*>\\s*\\|")
                    .rightLiteral("\\|\\s*<\\s*\\*\\s*Font\\s*=\\s*I\\s*\\*\\s*\\|")
                    .build(),
            ParseTextElement.builder()
                    .leftPartHtmlReplacement("<u>")
                    .rightPartHtmlReplacement("</u>")
                    .leftLiteral("\\|\\s*\\*\\s*Font\\s*=\\s*U\\s*\\*\\s*>\\s*\\|")
                    .rightLiteral("\\|\\s*<\\s*\\*\\s*Font\\s*=\\s*U\\s*\\*\\s*\\|")
                    .build()
    );

    public static final List<ParseTextElement> FONT_PARSE_ELEMENTS = List.of(
            ParseTextElement.builder()
                    .leftPartHtmlReplacement("<span style=\"font-size:")
                    .rightPartHtmlReplacement("</span>")
                    .leftLiteral("\\|\\s*\\*\\s*Size\\s*=\\s*\\d+\\s*\\*\\s*>\\s*\\|")
                    .rightLiteral("\\|\\s*<\\s*\\*\\s*Size\\s*=\\s*\\d+\\s*\\*\\s*\\|")
                    .build(),
            ParseTextElement.builder()
                    .leftPartHtmlReplacement("<span style=\"color:")
                    .rightPartHtmlReplacement("</span>")
                    .leftLiteral("\\|\\s*\\*\\s*Font_Color\\s*=\\s*(Red|Green|Blue|Yellow|Black|White|Magenta|Brown|Cyan|Gray)\\s*\\*\\s*>\\s*\\|")
                    .rightLiteral("\\|\\s*<\\s*\\*\\s*Font_Color\\s*=\\s*(Red|Green|Blue|Yellow|Black|White|Magenta|Brown|Cyan|Gray)\\s*\\*\\s*\\|")
                    .build()
    );


    public static final String SINGLE_FREE_WRONG_ANSWER = "\\$\\s*\\(\\s*_Wrong\\s*,\\s*-\\d+\\s*\\)\\s*.*";
    public static final String SINGLE_FREE_CORRECT_ANSWER = "\\$\\s*\\(\\s*_Correct\\s*,\\s*\\d+\\s*\\)\\s*.*";
    public static final String ANSWER_REACTION = "\\$\\s*\\(\\s*_Reaction\\s*\\)\\s*.*";
    public static final String UNDEFINED_ANSWER = "\\$\\s*\\(\\s*_Undefined\\s*\\)\\s*.*";
    public static final String MULTIPLE_WRONG_ANSWER = "^\\s*\\$\\s*\\(\\s*_Wrong\\s*\\).*$";
    public static final String MULTIPLE_CORRECT_ANSWER= "^\\s*\\$\\s*\\(\\s*_Correct\\s*\\).*$";


    @Data
    @Builder
    public static final class ParseMediaElement {
        private String leftPartHtmlReplacement;
        private String rightPartHtmlReplacement;
        private String literal;
        private String literalLeftPartReplacement;
        private String literalRightPartReplacement;
    }

    @Data
    @Builder
    public static final class ParseTextElement {
        private String leftPartHtmlReplacement;
        private String rightPartHtmlReplacement;
        private String leftLiteral;
        private String rightLiteral;
    }
}
