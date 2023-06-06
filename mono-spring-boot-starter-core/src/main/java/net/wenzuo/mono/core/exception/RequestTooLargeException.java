package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class RequestTooLargeException extends HttpStatusException {

    public RequestTooLargeException(String message) {
        super(413, message);
    }

    public RequestTooLargeException(String format, Object... args) {
        super(413, String.format(format, args));
    }

    public RequestTooLargeException(Throwable t) {
        super(413, t);
    }

    public RequestTooLargeException(String message, Throwable t) {
        super(413, message, t);
    }

}
