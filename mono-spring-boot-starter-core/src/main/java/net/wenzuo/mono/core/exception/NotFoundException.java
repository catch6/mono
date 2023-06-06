package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class NotFoundException extends HttpStatusException {

    public NotFoundException(String message) {
        super(404, message);
    }

    public NotFoundException(String format, Object... args) {
        super(404, String.format(format, args));
    }

    public NotFoundException(Throwable t) {
        super(404, t);
    }

    public NotFoundException(String message, Throwable t) {
        super(404, message, t);
    }

}
