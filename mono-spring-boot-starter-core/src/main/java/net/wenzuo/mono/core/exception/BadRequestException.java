package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class BadRequestException extends HttpStatusException {

    public BadRequestException(String message) {
        super(400, message);
    }

    public BadRequestException(String format, Object... args) {
        super(400, String.format(format, args));
    }

    public BadRequestException(Throwable t) {
        super(400, t);
    }

    public BadRequestException(String message, Throwable t) {
        super(400, message, t);
    }

}
