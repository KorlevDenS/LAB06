package server.commands;

import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import server.interfaces.Operand;
import server.interfaces.RemovingIf;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class {@code RemoveById} is used for creating command "remove_by_id" object,
 * that removes {@code MusicBand} object from the {@code HashSet}
 * with the same id as id from input.
 */
public class RemoveByID extends Command implements Operand, RemovingIf {

    /**
     * Field for id to remove {@code MusicBand} objects by.
     * Is always completed by {@link RemoveByID#installOperand(String)}.
     */
    private long idToRemoveBy;

    /**
     * Constructs new RemoveById object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveByID(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_BY_ID)
            throw new IncorrectDataForObjectException("Class RemoveById cannot perform this task");
    }

    public void analyseAndRemove() throws InvalidDataFromFileException {
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ? " +
                    "AND band_id = ?");
            stat.setLong(1, getClientId());
            stat.setLong(2, idToRemoveBy);
            int deleteCount = stat.executeUpdate();
            stat.close();
            conn.close();
            manager.sqlCollectionToMemory();
            if (deleteCount > 0)
                report.getReports().add("Из коллекции был удален ваш элемент под номером " + idToRemoveBy);
            else report.getReports().add("Элемента с таким ID в не было найдено среди ваших в коллекции.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка удаления из базы данных: " + e.getMessage());
            else report.getReports().add("Ошибка удаления из базы данных: " + e.getMessage());
        }
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        analyseAndRemove();
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

    public void installOperand(String stringRepresentation) {
        idToRemoveBy = Long.parseLong(stringRepresentation);
    }
}
