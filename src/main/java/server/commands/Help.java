package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.IncorrectDataForObjectException;
import server.ResponseHandler;
import server.ServerDataInstaller;

import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * Class {@code Help} is used for creating command "help" object,
 * that prints a manual with all commands and their descriptions.
 */
public class Help extends Command {

    /**
     * Constructs new {@code Help} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Help(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.HELP)
            throw new IncorrectDataForObjectException("Class Help cannot perform this task");
    }

    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        Arrays.stream(AvailableCommands.values())
                .forEach(s -> report.getReports().add("Команда " + s.getTitle() + " - " + s.getDescription() + "."));

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
