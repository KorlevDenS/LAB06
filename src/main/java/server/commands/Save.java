package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.IncorrectDataForObjectException;
import server.ResponseHandler;
import server.ServerDataInstaller;

import java.io.ObjectOutputStream;

/**
 * Class {@code Save} is used for creating command "save" objects,
 * that save current {@code MusicBand} {@code HashSet} to Xml file.
 */
public class Save extends Command {

    /**
     * Constructs new Save object.
     *
     * @param command enum constant from {@link AvailableCommands}
     */
    public Save(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.SAVE)
            throw new IncorrectDataForObjectException("Class Save cannot perform this task");
    }

    /**
     * Saves current collection to current xml file.
     */
    private void saveCollection() {
    }

    public synchronized void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        saveCollection();
        report.getReports().add("Текущая версия коллекции успешно сохранена в файл.");

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

}
