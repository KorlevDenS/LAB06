package commands;

import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;

/**
 * Class {@code Exit} is used for creating command "exit" object,
 * that shuts down the program.
 */
public class Exit extends Command {

    /**
     * Constructs new {@code Exit} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Exit(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.EXIT)
            throw new IncorrectDataForObjectException("Class Exit cannot perform this task");
    }

    /**
     * @return the description of the operation it is used in.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Executes the operation it is used in and prints a message.
     */
    @Override
    public ResultPattern execute() throws InvalidDataFromFileException {
        report.getReports().add("Завершение работы...");
        report.setTimeToExit(true);
        return report;
    }
}
