package Parser;

public class ParsingException extends Exception {

    public ParsingException() {
        super();
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ParsingException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
