package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.ScriptCommandManager;

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
     * Executes the operation it is used in and prints a message.
     */
    @Override
    public ResultPattern execute() throws InvalidDataFromFileException {
        report = new ResultPattern();
        report.getReports().add("Завершение работы...");
        report.setTimeToExit(true);
        ScriptCommandManager manager = new ScriptCommandManager("save",scriptScanner);
        ResultPattern savePattern = manager.execution(manager.instructionFetch());
        report.getReports().addAll(savePattern.getReports());
        return report;
    }
}
