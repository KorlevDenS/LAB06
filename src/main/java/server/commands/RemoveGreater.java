package server.commands;

import common.TransportedData;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.RemovingIf;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class {@code RemoveGreater} is used for creating command "remove_greater" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * that are greater than inputted one.
 */
public class RemoveGreater extends Add implements RemovingIf {

    /**
     * Constructs new RemoveGreater object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveGreater(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_GREATER)
            throw new IncorrectDataForObjectException("Class RemoveGreater cannot perform this task");
    }

    public void analyseAndRemove() throws InvalidDataFromFileException {
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ? " +
                    "AND number_of_participants > ?");
            stat.setLong(1, getClientId());
            stat.setLong(2, newBand.getNumberOfParticipants());
            int deleteCount = stat.executeUpdate();
            stat.close();
            conn.close();
            manager.sqlCollectionToMemory();
            if (deleteCount > 0)
                report.getReports().add("Из коллекции было удалено " + deleteCount + " ваших элементов");
            else report.getReports().add("Ни один из ваших элементов не превышает данный. Ничего не было удалено.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка удаления из базы данных: " + e.getMessage());
            else report.getReports().add("Ошибка удаления из базы данных: " + e.getMessage());
        }
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        analyseAndRemove();
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

}
