package server.commands;

import common.TransportedData;
import common.basic.MusicBand;
import common.exceptions.IncorrectDataForObjectException;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;
import common.AvailableCommands;
import common.ResultPattern;

import java.io.ObjectOutputStream;
import java.util.Collections;

/**
 * Class {@code Info} is used for creating command "info" object,
 * that prints information about current condition of {@code HashSet}
 * with {@code MusicBand} objects.
 */
public class Info extends Command {

    /**
     * Type of collection received by {@code getClass().getName()}
     */
    private String typeOfCollection;

    private long amountOfOwners;

    /**
     * Current amount of elements in {@code HashSet}
     */
    private int amountOfElements;
    /**
     * Type of {@code HashSet} inner elements.
     */
    private String typeOfInnerElements;
    /**
     * Current minimum element of {@code HashSet}.
     */
    private String minElement;
    /**
     * Current maximum element of {@code HashSet}.
     */
    private String maxElement;

    /**
     * Constructs new {@code Info} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Info(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.INFO)
            throw new IncorrectDataForObjectException("Class Info cannot perform this task");
    }

    /**
     * Analysing and filling current data about {@code HashSet}.
     */
    private void knowInformation() {
        amountOfOwners = ServerStatusRegister.appleMusic.stream().map(MusicBand::getClientId).distinct().count();
        typeOfCollection = ServerStatusRegister.appleMusic.getClass().getName();
        amountOfElements = ServerStatusRegister.appleMusic.size();
        if (!ServerStatusRegister.appleMusic.isEmpty()) {
            minElement = Collections.min(ServerStatusRegister.appleMusic).toString();
            maxElement = Collections.max(ServerStatusRegister.appleMusic).toString();
            typeOfInnerElements = Collections.min(ServerStatusRegister.appleMusic).getClass().getName();
        }
    }

    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        knowInformation();
        report.getReports().add("Информация о созданной коллекции:");
        report.getReports().add("Коллекция представляет собой " + typeOfCollection);
        report.getReports().add("Текущее количество элементов: " + amountOfElements);
        report.getReports().add("Текущее количество владельцев элементов: " + amountOfOwners);
        if (ServerStatusRegister.appleMusic.isEmpty()) {
            report.getReports().add("Минимального элемента ещё нет, коллекция пуста.");
            report.getReports().add("Максимального элемента ёщё нет, коллекция пуста.");
            report.getReports().add("Тип неизвестен, так как в коллекции еще нет ни одного элемента.");
        } else {
            report.getReports().add("Минимальный элемент: " + minElement);
            report.getReports().add("Максимальный элемент: " + maxElement);
            report.getReports().add("Тип хранимых элементов: " + typeOfInnerElements);
        }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
