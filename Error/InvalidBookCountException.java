package Error;

public class InvalidBookCountException extends IllegalArgumentException {
    public InvalidBookCountException(String message) {
        super(message);
    }
}