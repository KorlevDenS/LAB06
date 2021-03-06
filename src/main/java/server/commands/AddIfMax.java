package server.commands;

import common.TransportedData;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;

import java.io.ObjectOutputStream;
import java.util.Collections;

/**
 * Class AddIfMax is used for creating command "add_if_max" object,
 * that add {@code MusicBand} object in the {@code HashSet} if the object
 * is larger than all elements in this {@code HashSet}.
 */
public class AddIfMax extends Add {

    /**
     * Constructs new AddIfMax object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public AddIfMax(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.ADD_IF_MAX)
            throw new IncorrectDataForObjectException("Class AddIfMax cannot perform this task");
    }

    /**
     * Adds a new MusicBand object to the {@code HashSwt} if is lager then
     * all elements in the collection or if the collection {@code isEmpty()}.
     */
    @Override
    public void addElement() throws InvalidDataFromFileException {
        if (ServerStatusRegister.appleMusic.isEmpty()) {
            super.addElement();
            report.getReports().add("Новый элемент оказался больше всех имеющихся в коллекции.");
        } else {
            if (newBand.compareTo(Collections.max(ServerStatusRegister.appleMusic)) > 0) {
                super.addElement();
                report.getReports().add("Новый элемент оказался больше всех имеющихся в коллекции.");
            } else {
                report.getReports().add("В коллекции есть элементы больше данного.");
                report.getReports().add("Элемент в неё не добавлен.");
            }
        }
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        addElement();
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
