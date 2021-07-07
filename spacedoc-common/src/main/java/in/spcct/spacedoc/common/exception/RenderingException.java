package in.spcct.spacedoc.common.exception;

/**
 * An exception to be thrown in case something goes wrong while rendering something.
 */
public class RenderingException extends Exception {
    public RenderingException() {
    }

    public RenderingException(String message) {
        super(message);
    }

    public RenderingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderingException(Throwable cause) {
        super(cause);
    }
}
