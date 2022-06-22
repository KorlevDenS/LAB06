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
 * Class AddIfMin is used for creating command "add_if_min" object,
 * that add {@code MusicBand} object in the {@code HashSet} if the object
 * is smaller than all elements in this {@code HashSet}.
 */
public class AddIfMin extends Add {

    /**
     * becomes {@code true} if {@link AddIfMin#addElement()} adds new
     * element to the collection.
     */
    private boolean isAdded;

    /**
     * Constructs new AddIfMin object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public AddIfMin(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.ADD_IF_MIN)
            throw new IncorrectDataForObjectException("Class AddIfMin cannot perform this task");
    }

    /**
     * Adds a new MusicBand object to the {@code HashSwt} if is smaller than
     * all elements in the collection or if the collection {@code isEmpty()}.
     */
    @Override
    public void addElement() {
        if (ServerStatusRegister.appleMusic.isEmpty()) {
            ServerStatusRegister.appleMusic.add(newBand);
            if (newBand.getFrontMan() != null)
                ServerStatusRegister.passports.add(newBand.getFrontMan().getPassportID());
            isAdded = true;
        } else {
            if (newBand.compareTo(Collections.min(ServerStatusRegister.appleMusic)) < 0) {
                ServerStatusRegister.appleMusic.add(newBand);
                if (newBand.getFrontMan() != null)
                    ServerStatusRegister.passports.add(newBand.getFrontMan().getPassportID());
                isAdded = true;
            } else {
                isAdded = false;
            }
        }
    }

    public void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        addElement();
        if (isAdded) {
            report.getReports().add("Новый элемент оказался меньше всех имеющихся в коллекции.");
            report.getReports().add("Он успешно добавлен в неё.");
        } else {
            report.getReports().add("В коллекции есть элементы меньше данного.");
            report.getReports().add("Элемент в неё не добавлен.");
        }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
