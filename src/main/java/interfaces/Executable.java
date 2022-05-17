package interfaces;

import common.ResultPattern;
import exceptions.InvalidDataFromFileException;

/**
 * Interface {@code Executable} requires implementing classes
 * to realise execution of some process.
 */
public interface Executable {
    /**
     * Executes the operation it is used in and prints a message.
     */
    ResultPattern execute() throws InvalidDataFromFileException;
}
