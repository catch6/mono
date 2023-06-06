package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class ServerException extends HttpStatusException {

    public ServerException(String message) {
        super(500, message);
    }

    public ServerException(String format, Object... args) {
        super(500, String.format(format, args));
    }

    public ServerException(Throwable t) {
        super(500, t);
    }

    public ServerException(String message, Throwable t) {
        super(500, message, t);
    }

}
