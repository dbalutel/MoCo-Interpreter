package md.balutsel.mocointerpreter.engine.exceptions;

public class GrammarException extends RuntimeException {
    public GrammarException() {
    }

    public GrammarException(String message) {
        super(message);
    }

    public GrammarException(String message, Throwable cause) {
        super(message, cause);
    }
}
