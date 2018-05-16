package md.balutsel.mocointerpreter.engine.exceptions;

public class CourseDeclarationException extends RuntimeException {
    public CourseDeclarationException() {
    }

    public CourseDeclarationException(String message) {
        super(message);
    }

    public CourseDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }
}
