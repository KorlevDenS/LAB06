package exceptions;

public class InvalidInputException extends Exception {

    /**
     * Class {@code InvalidInputException} is used for
     * generating exceptions when user is trying to input
     * incorrect but technically suitable data.
     */
    public InvalidInputException() {
    }

    /**
     * Constructs {@code InvalidInputException} object.
     * @param message contains detailed information about the mistake.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
