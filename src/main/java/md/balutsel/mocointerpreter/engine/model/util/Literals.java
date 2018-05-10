package md.balutsel.mocointerpreter.engine.model.util;

public class Literals {
    public static final String COMMENT = "@";

    public static final String COURSE_START_LITERAL = "^\\s*_Course\\s*\\(\".+\"\\)\\s*$";
    public static final String COURSE_END_LITERAL = "_End_of_Course";

    public static final String START_UP_LITERAL = "_Start_Up";
    public static final String END_START_UP_LITERAL = "_End_Start_Up";

    public static final String LESSON_START_LITERAL = "^\\s*_Lesson\\s*\\(\\s*\\d+\\s+\\w+( +\\w+)*(\\s*|\\s+\\[\\s*((\\d+|\\d+\\.\\.\\d+)|(\\d+|\\d+\\.\\.\\d+),\\s*(\\d+|\\d+\\.\\.\\d+))\\s*\\])\\s*\\)\\s*$";
    public static final String LESSON_END_LITERAL = "_End_of_Lesson";
}
