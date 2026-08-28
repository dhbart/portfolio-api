package dhbart.portfolioapi.exception;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException() {
        super("Request rate limit exceeded");
    }
}
