package in.spcct.spacedoc.cdi;

/**
 * Exception to be thrown on instance lookup failure.
 */
public class LookupException extends RuntimeException {
    public <T> LookupException(Class<T> clazz) {
        this("Failed to lookup class " + clazz.getCanonicalName());
    }

    public LookupException() {
        super();
    }

    public LookupException(String detailMessage) {
        super(detailMessage);
    }

    public LookupException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public LookupException(Throwable throwable) {
        super(throwable);
    }
}
