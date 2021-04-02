package lebloism.pmt.shorturl.exception;

public class ShortUrlNotFoundException extends RuntimeException {

    public ShortUrlNotFoundException() {
        super();
    }

    public ShortUrlNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ShortUrlNotFoundException(final String message) {
        super(message);
    }

    public ShortUrlNotFoundException(final Throwable cause) {
        super(cause);
    }
}
