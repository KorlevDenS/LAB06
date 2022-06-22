package server.commands;

import common.TransportedData;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;

import java.io.ObjectOutputStream;

/**
 * Class Clear is used for creating command "clear" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}.
 */
public class Clear extends Command {

    /**
     * Constructs new Clear object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Clear(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.CLEAR)
            throw new IncorrectDataForObjectException("Class Clear cannot perform this task");
    }

    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        ServerStatusRegister.appleMusic.clear();
        ServerStatusRegister.passports.clear();
        report.getReports().add("Из коллекции были удалены все элементы.");

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
