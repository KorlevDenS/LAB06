package interfaces;

import commands.AvailableCommands;
import exceptions.InvalidDataFromFileException;

/**
 * Interface {@code CommandManagement} requires implementing classes
 * to realise methods to manage commands.
 */
public interface CommandManagement {
    /**
     * Method produces selection and validation of user instruction.
     * If there are no such instruction in {@link AvailableCommands}
     * {@code InstructionFetch} will ask user to enter valid one
     * from the list of available commands.
     *
     * @return {@link AvailableCommands} object.
     */
    AvailableCommands instructionFetch();

    /**
     * Method is used when the current command contains simple params.
     *
     * @return params as a one {@code String}.
     */
    String operandFetch();

    /**
     * Method is used to execute current instruction.
     * {@code execution} creates valid {@code Command} objects
     * and use their {@link Executable#execute()}.
     * Method also passes parameters to {@code Command}  objects.
     *
     * @param command {@link AvailableCommands} object.
     */
    void execution(AvailableCommands command) throws InvalidDataFromFileException;
}
