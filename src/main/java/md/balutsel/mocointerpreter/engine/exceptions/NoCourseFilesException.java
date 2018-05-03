package md.balutsel.mocointerpreter.engine.exceptions;

public class NoCourseFilesException extends RuntimeException {
    public NoCourseFilesException() {
    }

    public NoCourseFilesException(String message) {
        super(message);
    }

    public NoCourseFilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
