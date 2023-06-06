package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class GatewayTimeoutException extends HttpStatusException {

    public GatewayTimeoutException(String message) {
        super(504, message);
    }

    public GatewayTimeoutException(String format, Object... args) {
        super(504, String.format(format, args));
    }

    public GatewayTimeoutException(Throwable t) {
        super(504, t);
    }

    public GatewayTimeoutException(String message, Throwable t) {
        super(504, message, t);
    }

}
