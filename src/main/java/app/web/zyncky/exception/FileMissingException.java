package app.web.zyncky.exception;

public class FileMissingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileMissingException(String message) {
        super(message);
    }
}
