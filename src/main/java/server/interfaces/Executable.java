package server.interfaces;

import common.exceptions.InvalidDataFromFileException;

import java.io.ObjectOutputStream;

/**
 * Interface {@code Executable} requires implementing classes
 * to realise execution of some process.
 */
public interface Executable {
    /**
     * Executes the operation it is used in and prints a message.
     */
    void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException;
}
