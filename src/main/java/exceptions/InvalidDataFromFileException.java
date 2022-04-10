package exceptions;

/**
 * Class {@code InvalidDataFromFileException} is used for
 * generating exceptions when user is trying to load
 * incorrect data from file for building new objects.
 */
public class InvalidDataFromFileException extends Exception {

    /**
     * Constructs {@code InvalidDataFromFileException} object.
     */
    public InvalidDataFromFileException() {
    }

    /**
     * Constructs {@code InvalidDataFromFileException} object.
     *
     * @param message contains detailed information about the mistake.
     */
    public InvalidDataFromFileException(String message) {
        super(message);
    }
}
