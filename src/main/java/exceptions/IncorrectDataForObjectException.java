package exceptions;

/**
 * Class {@code IncorrectDataForObjectException} is used for
 * generating exceptions when trying to pass incorrect
 * but technically suitable for creating a new object
 * parameters to the constructor.
 */
public class IncorrectDataForObjectException extends RuntimeException {

    /**
     * Constructs {@code IncorrectDataForObjectException} object.
     *
     * @param message contains detailed information about the mistake.
     */
    public IncorrectDataForObjectException(String message) {
        super(message);
    }

}
