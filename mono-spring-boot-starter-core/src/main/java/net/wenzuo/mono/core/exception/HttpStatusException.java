package net.wenzuo.mono.core.exception;

/**
 * @author Catch
 * @since 2023-05-30
 */
public class HttpStatusException extends RuntimeException {

    private final int status;

    protected HttpStatusException(int status, String message) {
        super(message);
        this.status = status;
    }

    protected HttpStatusException(int status, String format, Object... args) {
        super(String.format(format, args));
        this.status = status;
    }

    protected HttpStatusException(int status, Throwable t) {
        super(t);
        this.status = status;
    }

    protected HttpStatusException(int status, String message, Throwable t) {
        super(message, t);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
