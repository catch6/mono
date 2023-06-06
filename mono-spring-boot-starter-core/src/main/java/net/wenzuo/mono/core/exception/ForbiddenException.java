package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class ForbiddenException extends HttpStatusException {

    public ForbiddenException(String message) {
        super(403, message);
    }

    public ForbiddenException(String format, Object... args) {
        super(403, String.format(format, args));
    }

    public ForbiddenException(Throwable t) {
        super(403, t);
    }

    public ForbiddenException(String message, Throwable t) {
        super(403, message, t);
    }

}
