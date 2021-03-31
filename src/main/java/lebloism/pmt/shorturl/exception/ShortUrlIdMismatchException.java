package lebloism.pmt.shorturl.exception;

public class ShortUrlIdMismatchException extends RuntimeException {

    public ShortUrlIdMismatchException() {
        super();
    }

    public ShortUrlIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ShortUrlIdMismatchException(final String message) {
        super(message);
    }

    public ShortUrlIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
