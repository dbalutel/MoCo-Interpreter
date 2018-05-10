package md.balutsel.mocointerpreter.engine.exceptions;

public class NoDeclaredCourseException extends RuntimeException {
    public NoDeclaredCourseException() {
    }

    public NoDeclaredCourseException(String message) {
        super(message);
    }

    public NoDeclaredCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
