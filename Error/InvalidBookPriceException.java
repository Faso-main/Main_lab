package Error;

public class InvalidBookPriceException extends IllegalArgumentException {
    public InvalidBookPriceException(String message) {
        super(message);
    }
}