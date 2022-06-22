package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.ResponseHandler;
import server.ScriptCommandManager;
import server.ServerDataInstaller;

import java.io.ObjectOutputStream;

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
    public void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        report.getReports().add("Завершение работы...");
        report.setTimeToExit(true);
        ScriptCommandManager manager = new ScriptCommandManager("save", scriptScanner);
        manager.execution(manager.instructionFetch(), sendToClient);
        //report.getReports().addAll(savePattern.getReports());

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
