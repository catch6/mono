package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class UnauthorizedException extends HttpStatusException {

    public UnauthorizedException(String message) {
        super(401, message);
    }

    public UnauthorizedException(String format, Object... args) {
        super(401, String.format(format, args));
    }

    public UnauthorizedException(Throwable t) {
        super(401, t);
    }

    public UnauthorizedException(String message, Throwable t) {
        super(401, message, t);
    }

}
