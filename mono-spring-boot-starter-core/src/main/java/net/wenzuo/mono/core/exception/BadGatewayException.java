package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class BadGatewayException extends HttpStatusException {

    public BadGatewayException(String message) {
        super(502, message);
    }

    public BadGatewayException(String format, Object... args) {
        super(502, String.format(format, args));
    }

    public BadGatewayException(Throwable t) {
        super(502, t);
    }

    public BadGatewayException(String message, Throwable t) {
        super(502, message, t);
    }

}
