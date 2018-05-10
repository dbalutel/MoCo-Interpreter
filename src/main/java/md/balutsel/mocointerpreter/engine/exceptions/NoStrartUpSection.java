package md.balutsel.mocointerpreter.engine.exceptions;

public class NoStrartUpSection extends RuntimeException {
    public NoStrartUpSection() {
    }

    public NoStrartUpSection(String message) {
        super(message);
    }

    public NoStrartUpSection(String message, Throwable cause) {
        super(message, cause);
    }
}
