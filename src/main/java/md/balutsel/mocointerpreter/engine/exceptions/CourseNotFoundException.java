package md.balutsel.mocointerpreter.engine.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() {
    }

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
