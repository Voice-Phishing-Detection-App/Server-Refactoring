package PhishingUniv.Phinocchio.exception.Login;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }
}