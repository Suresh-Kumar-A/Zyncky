package app.web.zyncky.exception;

public class UserMissingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserMissingException(String message) {
        super(message);
    }
}
