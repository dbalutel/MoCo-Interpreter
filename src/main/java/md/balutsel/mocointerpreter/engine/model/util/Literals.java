package md.balutsel.mocointerpreter.engine.model.util;

public class Literals {
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

    public static final String PART_START_LITERAL = "^_Part\\s*\\((\\d+|All)\\)$";
    public static final String PART_SECTION = "_Part\\s*\\((\\d+|All)\\)\\n(.*\\n)((?!_End_of_Part).*\\n)*_End_of_Part";

    public static final String QUESTION_START_LITERAL = "^_Question\\s*\\(\\s*(_Single|_Free|_Multiple)\\s*,\\s*\\d+\\s*\\)\\s*.*$";
    public static final String QUESTION_SECTION = "_Question\\s*\\(\\s*(_Single|_Free|_Multiple)\\s*,\\s*\\d+\\s*\\)\\s*.*\\n((?!_End_of_Question).*\\n)*_End_of_Question";

    public static final String FREE_QUESTION = "_Free";
    public static final String SINGLE_QUESTION = "_Single";
    public static final String MULTIPLE_QUESTION = "_Multiple";
}
